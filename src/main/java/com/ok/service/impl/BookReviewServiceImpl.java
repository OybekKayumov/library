package com.ok.service.impl;

import com.ok.model.Book;
import com.ok.model.BookReview;
import com.ok.model.User;
import com.ok.payload.dto.BookReviewDTO;
import com.ok.payload.request.CreateReviewRequest;
import com.ok.payload.request.UpdateReviewRequest;
import com.ok.payload.response.PageResponse;
import com.ok.repo.BookRepo;
import com.ok.repo.BookReviewRepo;
import com.ok.service.BookReviewService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

	private final BookReviewRepo bookReviewRepo;
	private final UserService userService;
	private final BookRepo bookRepo;

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

		return null;
	}

	@Override
	public BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) {
		return null;
	}

	@Override
	public void deleteReview(Long reviewId) {

	}

	@Override
	public PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) {
		return null;
	}

	private boolean hasUserReadBook(Long userId, Long bookId) {
		return false;
	}
}
