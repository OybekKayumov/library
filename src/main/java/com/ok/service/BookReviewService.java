package com.ok.service;

import com.ok.payload.dto.BookReviewDTO;
import com.ok.payload.request.CreateReviewRequest;

public interface BookReviewService {

	BookReviewDTO createReview(CreateReviewRequest request);

	BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request);


}
