package com.ok.service.impl;

import com.ok.model.Book;
import com.ok.model.User;
import com.ok.model.Wishlist;
import com.ok.payload.dto.WishlistDTO;
import com.ok.payload.response.PageResponse;
import com.ok.repo.BookRepo;
import com.ok.repo.WishlistRepo;
import com.ok.service.UserService;
import com.ok.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

	private final WishlistRepo wishlistRepo;
	private final UserService userService;
	private final BookRepo bookRepo;

	@Override
	public WishlistDTO addToWishlist(Long bookId, String notes) throws Exception {

		User user = userService.getCurrentUser();

		Book book = bookRepo.findById(bookId)
						.orElseThrow(() -> new Exception("Book not found"));

		if (wishlistRepo.existsByUserIdAndBookId(user.getId(), book.getId())) {

			throw new Exception("Book already exists in Wishlist");
		}

		Wishlist wishlist = new Wishlist();
		wishlist.setUser(user);
		wishlist.setBook(book);
		wishlist.setNotes(notes);
		Wishlist saved = wishlistRepo.save(wishlist);


		return null;
	}

	@Override
	public void removeFromWishlist(Long bookId) {

	}

	@Override
	public PageResponse<WishlistDTO> getMyWishlist(int page, int size) {
		return null;
	}
}
