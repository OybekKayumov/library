package com.ok.controller;

import com.ok.payload.dto.BookReviewDTO;
import com.ok.payload.request.CreateReviewRequest;
import com.ok.payload.request.UpdateReviewRequest;
import com.ok.payload.response.ApiResponse;
import com.ok.payload.response.PageResponse;
import com.ok.service.BookReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class BookReviewController {

	private final BookReviewService bookReviewService;

	@PostMapping
	public ResponseEntity<?> createReview(
					@Valid @RequestBody CreateReviewRequest request) throws Exception{

		BookReviewDTO reviewDTO = bookReviewService.createReview(request);

		return ResponseEntity.ok(reviewDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateReview(
					@PathVariable Long id,
					@Valid @RequestBody UpdateReviewRequest request) throws Exception{

		BookReviewDTO reviewDTO = bookReviewService.updateReview(id, request);

		return ResponseEntity.ok(reviewDTO);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) throws Exception{

		bookReviewService.deleteReview(reviewId);
		return ResponseEntity.ok(
						new ApiResponse("Review deleted successfully",true));
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<?> getReviewByBook(
					@PathVariable Long bookId,
					@RequestParam(defaultValue = "0") int page,
					@RequestParam(defaultValue = "10") int size) throws Exception{

		PageResponse<BookReviewDTO> reviews = bookReviewService
						.getReviewsByBookId(bookId, page, size);

		return ResponseEntity.ok(reviews);
	}

}
