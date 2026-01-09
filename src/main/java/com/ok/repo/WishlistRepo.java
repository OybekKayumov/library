package com.ok.repo;

import com.ok.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

	List<Wishlist> findByUserId(Long userId);

	boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
