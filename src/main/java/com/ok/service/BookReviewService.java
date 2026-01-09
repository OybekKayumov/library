package com.ok.service;

import com.ok.payload.dto.BookReviewDTO;
import com.ok.payload.request.CreateReviewRequest;
import com.ok.payload.request.UpdateReviewRequest;
import com.ok.payload.response.PageResponse;
import org.springframework.data.domain.PageRequest;

public interface BookReviewService {

	BookReviewDTO createReview(CreateReviewRequest request) throws Exception;

	BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request);

	void deleteReview(Long reviewId);

	PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size);
}
