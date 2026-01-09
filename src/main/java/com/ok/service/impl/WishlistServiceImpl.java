package com.ok.service.impl;

import com.ok.mapper.WishlistMapper;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

	private final WishlistRepo wishlistRepo;
	private final UserService userService;
	private final BookRepo bookRepo;
	private final WishlistMapper wishlistMapper;

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

		return wishlistMapper.toDTO(saved);
	}

	@Override
	public void removeFromWishlist(Long bookId) throws Exception {

		User user = userService.getCurrentUser();

		Wishlist wishlist = wishlistRepo.findByUserIdAndBookId(user.getId(), bookId);

		if (wishlist == null) {

			throw new Exception("Book not found");
		}

		wishlistRepo.delete(wishlist);

	}

	@Override
	public PageResponse<WishlistDTO> getMyWishlist(int page, int size) throws Exception {

		Long userId = userService.getCurrentUser().getId();

		Pageable pageable = PageRequest.of(page, size,
						Sort.by("addedAt").descending());

		Page<Wishlist> wishlistPage = wishlistRepo.findByUserId(userId, pageable);

		return convertToPageResponse(wishlistPage);
	}

	private  PageResponse<WishlistDTO> convertToPageResponse(Page<Wishlist> wishlistPage) {
		List<WishlistDTO> wishlistDTOs = wishlistPage.getContent()
						.stream()
						.map(wishlistMapper::toDTO)
						.collect(Collectors.toList());

		return new PageResponse<>(
						wishlistDTOs,
						wishlistPage.getNumber(),
						wishlistPage.getSize(),
						wishlistPage.getTotalElements(),
						wishlistPage.getTotalPages(),
						wishlistPage.isLast(),
						wishlistPage.isFirst(),
						wishlistPage.isEmpty()
		);
	}

}
