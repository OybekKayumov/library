package com.ok.service;

import com.ok.payload.dto.WishlistDTO;
import com.ok.payload.response.PageResponse;

public interface WishlistService {

	WishlistDTO addToWishlist(Long bookId, String notes) throws Exception;

	void removeFromWishlist(Long bookId) throws Exception;

	PageResponse<WishlistDTO> getMyWishlist(int page, int size) throws Exception;

}
