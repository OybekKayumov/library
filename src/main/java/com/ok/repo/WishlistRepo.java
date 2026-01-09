package com.ok.repo;

import com.ok.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

	Page<Wishlist> findByUserId(Long userId, Pageable pageable);

	boolean existsByUserIdAndBookId(Long userId, Long bookId);

	void deleteByUserIdAndBookId(Long userId, Long bookId);
}
