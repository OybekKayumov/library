package com.ok.service.impl;

import com.ok.domain.BookLoanStatus;
import com.ok.mapper.BookReviewMapper;
import com.ok.model.Book;
import com.ok.model.BookLoan;
import com.ok.model.BookReview;
import com.ok.model.User;
import com.ok.payload.dto.BookReviewDTO;
import com.ok.payload.request.CreateReviewRequest;
import com.ok.payload.request.UpdateReviewRequest;
import com.ok.payload.response.PageResponse;
import com.ok.repo.BookLoanRepo;
import com.ok.repo.BookRepo;
import com.ok.repo.BookReviewRepo;
import com.ok.service.BookReviewService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

	private final BookReviewRepo bookReviewRepo;
	private final UserService userService;
	private final BookRepo bookRepo;
	private final BookReviewMapper bookReviewMapper;
	private final BookLoanRepo bookLoanRepo;

	@Override
	public BookReviewDTO createReview(CreateReviewRequest request) throws Exception {

		User user = userService.getCurrentUser();

		Book book = bookRepo.findById(request.getBookId())
						.orElseThrow(() -> new Exception("Book not found") );

		if (bookReviewRepo.existsByUserIdAndBookId(user.getId(), book.getId()) ) {

			throw new Exception("Book review already exists") ;
		}

		boolean hasReadBook = hasUserReadBook(user.getId(), book.getId());

		if (!hasReadBook) {
			throw new Exception("You have not read this book") ;
		}

		BookReview bookReview = new BookReview();
		bookReview.setUser(user);
		bookReview.setBook(book);
		bookReview.setRating(request.getRating());
		bookReview.setReviewText(request.getReviewText());
		bookReview.setTitle(request.getTitle());

		BookReview savedBookReview = bookReviewRepo.save(bookReview);

		return bookReviewMapper.toDTO(savedBookReview);
	}

	@Override
	public BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) throws Exception {

		User user = userService.getCurrentUser();

		BookReview bookReview = bookReviewRepo.findById(reviewId)
						.orElseThrow(() -> new Exception("Review not found"));

		if (!bookReview.getUser().getId().equals(user.getId())) {

			throw new Exception("You have not reviewed this book") ;
		}

		bookReview.setReviewText(request.getReviewText());
		bookReview.setTitle(request.getTitle());
		bookReview.setRating(request.getRating());

		BookReview savedBookReview = bookReviewRepo.save(bookReview);

		return bookReviewMapper.toDTO(savedBookReview);
	}

	@Override
	public void deleteReview(Long reviewId) throws Exception {

		User currentUser = userService.getCurrentUser();

		BookReview bookReview = bookReviewRepo.findById(reviewId)
						.orElseThrow(() -> new Exception("Review not found"));

		if (!bookReview.getUser().getId().equals(currentUser.getId())) {
			throw new Exception("You can only delete your owns reviews") ;
		}

		bookReviewRepo.delete(bookReview);
	}

	@Override
	public PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) throws Exception {

		Book book = bookRepo.findById(id)
						.orElseThrow(() -> new Exception("Book not found by id!"));

		Pageable pageable = PageRequest.of(
						page, size, Sort.by("createdAt").descending());

		Page<BookReview> reviewPage = bookReviewRepo.findByBook(book, pageable);

		return convertToPageResponse(reviewPage);
	}

	private PageResponse<BookReviewDTO> convertToPageResponse(Page<BookReview> reviewPage) {

		List<BookReviewDTO> reviewDTOs = reviewPage.getContent()
						.stream()
						.map(bookReviewMapper::toDTO)
						.collect(Collectors.toList());

		return new PageResponse<>(
						reviewDTOs,
						reviewPage.getNumber(),
						reviewPage.getSize(),
						reviewPage.getTotalElements(),
						reviewPage.getTotalPages(),
						reviewPage.isLast(),
						reviewPage.isFirst(),
						reviewPage.isEmpty()
		);

	}

	private boolean hasUserReadBook(Long userId, Long bookId) {

		List<BookLoan> bookLoans = bookLoanRepo.findByBookId(bookId);

		return bookLoans
						.stream()
						.anyMatch(bookLoan -> bookLoan.getUser().getId().equals(userId)
						&& bookLoan.getStatus() == BookLoanStatus.RETURNED);
	}

}
