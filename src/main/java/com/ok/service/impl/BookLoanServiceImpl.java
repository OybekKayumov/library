package com.ok.service.impl;

import com.ok.domain.BookLoanStatus;
import com.ok.domain.BookLoanType;
import com.ok.exception.BookException;
import com.ok.mapper.BookLoanMapper;
import com.ok.model.Book;
import com.ok.model.BookLoan;
import com.ok.model.Subscription;
import com.ok.model.User;
import com.ok.payload.dto.BookLoanDTO;
import com.ok.payload.dto.SubscriptionDTO;
import com.ok.payload.request.BookLoanSearchRequest;
import com.ok.payload.request.CheckinRequest;
import com.ok.payload.request.CheckoutRequest;
import com.ok.payload.request.RenewalRequest;
import com.ok.payload.response.PageResponse;
import com.ok.repo.BookLoanRepo;
import com.ok.repo.BookRepo;
import com.ok.service.BookLoanService;
import com.ok.service.SubscriptionService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

	private final UserService userService;
	private final SubscriptionService subscriptionService;
	private final BookRepo bookRepo;
	private final BookLoanRepo bookLoanRepo;
	private final BookLoanMapper bookLoanMapper;

	@Override
	public BookLoanDTO checkOutBook(CheckoutRequest checkoutRequest) throws Exception {

		User user = userService.getCurrentUser();

		return checkoutBookForUser(user.getId(), checkoutRequest);

	}

	@Override
	public BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest) throws Exception {
		//* 1 - validate user exist
		User user = userService.findById(userId);

		//* 2 - validate user has active subscription
		SubscriptionDTO subscription =
						subscriptionService.getUsersActiveSubscriptions(userId);

		//* 3 - validate book exists and available
		Book book = bookRepo.findById(checkoutRequest.getBookId())
						.orElseThrow(() -> new BookException("Book not found with ID " + checkoutRequest.getBookId()));

		if (!book.getActive()) {
			throw new BookException("Book is not active.");
		}

		if (book.getAvailableCopies() <= 0) {
			throw new BookException("Book has no available copies.");
		}

		//* 4 - check if user already has this book checkout
		if (bookLoanRepo.hasActiveCheckout(userId, book.getId())) {
			throw new BookException("Book already has active checkout.");
		}

		//* 5 - check user's active checkout limit
		long activeCheckouts = bookLoanRepo.countActiveBookLoansByUser(userId);
		int maxBooksAllowed = subscription.getMaxBookAllowed();

		if (activeCheckouts >= maxBooksAllowed) {
			throw new Exception("You have reached the maximum allowed number of book loans.");
		}

		//* 6 - check for overdue books
		long overdueCount = bookLoanRepo.countOverdueBookLoansByUser(userId);
		if (overdueCount > 0) {
			throw new Exception("First return old overdue book!");
		}

		//* 7 - fine todo

		//* 8 - create book loan
		BookLoan bookLoan = BookLoan.builder()
						.user(user)
						.book(book)
						.type(BookLoanType.CHECKOUT)
						.status(BookLoanStatus.CHECKED_OUT)
						.checkoutDate(LocalDate.now())
						.dueDate(LocalDate.now().plusDays(checkoutRequest.getCheckoutDays()))
						.renewalCount(0)
						.maxRenewals(2)
						.notes(checkoutRequest.getNotes())
						.isOverdue(false)
						.overdueDays(0)
						.build();

		//* 9 - update book available copies
		book.setAvailableCopies(book.getAvailableCopies() -1);
		bookRepo.save(book);

		//* 10 - save book loan
		BookLoan savedBookLoan = bookLoanRepo.save(bookLoan);

		return bookLoanMapper.toDTO(savedBookLoan);

	}

	@Override
	public BookLoanDTO checkinBookBook(CheckinRequest checkinRequest) throws Exception {

		//* 1 - validate boo loan exist
		BookLoan bookLoan = bookLoanRepo.findById(checkinRequest.getBookLoanId())
						.orElseThrow(() -> new Exception("BookLoan not found!"));

		//* 2 - check if already returned
		if (!bookLoan.isActive()) {
			throw new BookException("Book loan is not active.");
		}

		//* 3 - set return date
		bookLoan.setReturnDate(LocalDate.now());

		//* 4 - check status
		BookLoanStatus condition = checkinRequest.getCondition();
		if (condition == null) {
			condition = BookLoanStatus.RETURNED;
		}

		//* 5 - fine - todo
		bookLoan.setOverdueDays(0);
		bookLoan.setIsOverdue(false);

		//* 6 - fine - todo
		bookLoan.setNotes("Book returned by user");

		//* 7 - update book availability
		if (condition != BookLoanStatus.LOST) {
			Book book = bookLoan.getBook();
			book.setAvailableCopies(book.getAvailableCopies() + 1);
			bookRepo.save(book);

			//* process next reservation - todo
		}

		//* 8
		BookLoan savedBookLoan = bookLoanRepo.save(bookLoan);

		return bookLoanMapper.toDTO(savedBookLoan);

	}

	@Override
	public BookLoanDTO renewCheckout(RenewalRequest renewalRequest) {
		return null;
	}

	@Override
	public PageResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) {
		return null;
	}

	@Override
	public PageResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request) {
		return null;
	}

	@Override
	public int updateOverdueBookLoan() {
		return 0;
	}
}
