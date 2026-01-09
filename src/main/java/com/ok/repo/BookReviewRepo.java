package com.ok.repo;

import com.ok.model.Book;
import com.ok.model.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepo extends JpaRepository<BookReview, Long> {

	Page<BookReview> findByBook(Book book, Pageable pageable);

	boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
