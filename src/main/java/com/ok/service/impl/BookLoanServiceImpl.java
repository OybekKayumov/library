package com.ok.service.impl;

import com.ok.domain.BookLoanStatus;
import com.ok.exception.BookException;
import com.ok.model.Book;
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

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

	private final UserService userService;
	private final SubscriptionService subscriptionService;
	private final BookRepo bookRepo;
	private final BookLoanRepo bookLoanRepo;

	@Override
	public BookLoanDTO checkOutBook(Long userId,
	                                CheckoutRequest checkoutRequest) throws Exception {

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

		return null;
	}

	@Override
	public BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest) {
		return null;
	}

	@Override
	public BookLoanDTO checkinBookBook(CheckinRequest checkinRequest) {
		return null;
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
