package com.ok.repo;

import com.ok.domain.BookLoanStatus;
import com.ok.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {

	@Query("select case when count(r) > 0 then true else FALSE end from " +
					"Reservation r where r.user.id = :userId and r.book.id = :bookId " +
					"and (r.status = 'PENDING' or r.status= 'AVAILABLE')")
	 boolean hasActiveReservation(
					 @Param("userId") Long userId,
					 @Param("bookId") Long bookId);

	@Query("select count(r) from Reservation r where r.user.id = :userId " +
					"and (r.status = 'PENDING' or r.status= 'AVAILABLE')")
	long countActiveReservationsByUser(@Param("userId") Long userId);

	@Query("select count(r) from Reservation r where r.book.id = :bookId " +
					"and r.status = 'PENDING'")
	long countPendingReservationsByBook(Long bookId);

}
