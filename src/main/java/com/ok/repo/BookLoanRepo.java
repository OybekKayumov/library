package com.ok.repo;

import com.ok.domain.BookLoanStatus;
import com.ok.model.BookLoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookLoanRepo extends JpaRepository<BookLoan, Long> {

	Page<BookLoan> findByUserId(Long userId, Pageable pageable);

	Page<BookLoan> findByUserIdAndStatus(Long userId, BookLoanStatus status,
	                                     Pageable pageable);

	Page<BookLoan> findByStatus(BookLoanStatus status, Pageable pageable);

	//! end!
	@Query("select case when count(bl) > 0 then true else false end from " +
					"BookLoan bl where bl.user.id =: userId AND bl.book.id =: bookId " +
					"AND (bl.status = 'CHECKED_OUT' OR bl.status = 'OVERDUE')")
	boolean hasActiveCheckout(
					@Param("userId") Long userId,
					@Param("bookId") Long bookId);

	@Query("select count(bl) from BookLoan bl where bl.user.id =: userId AND " +
					"(bl.status = 'CHECKED_OUT' OR bl.status = 'OVERDUE')")
	long countActiveBookLoansByUser(
					@Param("userId") Long userId);

	@Query("select count(bl) from BookLoan bl where bl.user.id =: userId AND " +
					"bl.status = 'OVERDUE'")
	long countOverdueBookLoansByUser(
					@Param("userId") Long userId);
}
