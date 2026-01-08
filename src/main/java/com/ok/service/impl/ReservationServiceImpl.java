package com.ok.service.impl;

import com.ok.domain.BookLoanStatus;
import com.ok.domain.ReservationStatus;
import com.ok.mapper.ReservationMapper;
import com.ok.model.Book;
import com.ok.model.Reservation;
import com.ok.model.User;
import com.ok.payload.dto.ReservationDTO;
import com.ok.payload.request.ReservationRequest;
import com.ok.payload.request.ReservationSearchRequest;
import com.ok.payload.response.PageResponse;
import com.ok.repo.BookLoanRepo;
import com.ok.repo.BookRepo;
import com.ok.repo.ReservationRepo;
import com.ok.service.ReservationService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.IllegalFormatCodePointException;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {


	private final BookLoanRepo bookLoanRepo;
	private final UserService userService;
	private final BookRepo bookRepo;
	private final ReservationRepo reservationRepo;
	private final ReservationMapper reservationMapper;

	int MAX_RESERVATIONS = 5;

	@Override
	public ReservationDTO createReservation(ReservationRequest reservationRequest) {
		return null;
	}

	@Override
	public ReservationDTO createReservationForUser(ReservationRequest reservationRequest, Long userId) throws Exception {

		boolean alreadyHasLoan = bookLoanRepo.existsByUserIdAndBookIdAndStatus(
						userId,
						reservationRequest.getBookId(),
						BookLoanStatus.CHECKED_OUT);

		if (alreadyHasLoan) {
			throw new Exception("You already have loan on this book");
		}

		User user = userService.getCurrentUser();

		Book book = bookRepo.findById(reservationRequest.getBookId())
						.orElseThrow(() -> new Exception("Book not found"));

		if (reservationRepo.hasActiveReservation(userId, book.getId())) {
			throw new Exception("You already have an active reservation");
		}

		if (book.getAvailableCopies() > 0) {
			throw new Exception("Book has already been available");
		}

		long activeReservations =
						reservationRepo.countActiveReservationsByUser(userId);

		if (activeReservations >= MAX_RESERVATIONS) {
			throw new Exception("You have reserved " + MAX_RESERVATIONS +" times");
		}

		Reservation reservation = new Reservation();
		reservation.setUser(user);
		reservation.setBook(book);
		reservation.setStatus(ReservationStatus.PENDING);
		reservation.setReservedAt(LocalDateTime.now());
		reservation.setNotificationSent(false);
		reservation.setNotes(reservationRequest.getNotes());

		long pendingCount = reservationRepo.countPendingReservationsByBook(
					book.getId());

		reservation.setQueuePosition((int) pendingCount + 1);

		Reservation savedReservation = reservationRepo.save(reservation);

		return reservationMapper.toDTO(savedReservation);

	}

	@Override
	public ReservationDTO cancelReservation(Long reservationId) {
		return null;
	}

	@Override
	public ReservationDTO fulfillReservation(Long reservationId) {
		return null;
	}

	@Override
	public PageResponse<ReservationDTO> getMyReservations(ReservationSearchRequest searchRequest) {
		return null;
	}

	@Override
	public PageResponse<ReservationDTO> searchReservations(ReservationSearchRequest searchRequest) {
		return null;
	}
}
