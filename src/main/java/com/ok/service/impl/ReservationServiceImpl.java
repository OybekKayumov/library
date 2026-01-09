package com.ok.service.impl;

import com.ok.domain.BookLoanStatus;
import com.ok.domain.ReservationStatus;
import com.ok.domain.UserRole;
import com.ok.mapper.ReservationMapper;
import com.ok.model.Book;
import com.ok.model.Reservation;
import com.ok.model.User;
import com.ok.payload.dto.ReservationDTO;
import com.ok.payload.request.CheckoutRequest;
import com.ok.payload.request.ReservationRequest;
import com.ok.payload.request.ReservationSearchRequest;
import com.ok.payload.response.PageResponse;
import com.ok.repo.BookLoanRepo;
import com.ok.repo.BookRepo;
import com.ok.repo.ReservationRepo;
import com.ok.service.BookLoanService;
import com.ok.service.ReservationService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.IllegalFormatCodePointException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {


	private final BookLoanRepo bookLoanRepo;
	private final UserService userService;
	private final BookRepo bookRepo;
	private final ReservationRepo reservationRepo;
	private final ReservationMapper reservationMapper;
	private final BookLoanService bookLoanService;

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
	public ReservationDTO cancelReservation(Long reservationId) throws Exception {

		Reservation reservation = reservationRepo.findById(reservationId)
						.orElseThrow(() -> new Exception("Reservation not found!"));

		User currentUser = userService.getCurrentUser();

		if (!reservation.getUser().equals(currentUser.getId()) &&
					currentUser.getRole() != UserRole.ROLE_ADMIN) {

			throw new Exception("You can only cancel your own reservations");
		}

		if (!reservation.canBeCancelled()) {

			throw new Exception("Reservation can not be cancelled");
		}

		reservation.setStatus(ReservationStatus.CANCELLED);
		reservation.setCancelledAt(LocalDateTime.now());

		Reservation savedReservation = reservationRepo.save(reservation);

		//updateQueuePositions(reservation.getBook().getId());

		return reservationMapper.toDTO(savedReservation);
	}

	@Override
	public ReservationDTO fulfillReservation(Long reservationId) throws Exception {

		Reservation reservation = reservationRepo.findById(reservationId)
						.orElseThrow(() -> new Exception("Reservation not found!"));

		if (reservation.getBook().getAvailableCopies() <= 0 ) {

			throw new Exception("Reservation is not available for pickup");
		}

		reservation.setStatus(ReservationStatus.FULFILLED);
		reservation.setFulfilledAt(LocalDateTime.now());

		Reservation savedReservation = reservationRepo.save(reservation);

		CheckoutRequest request = new CheckoutRequest();
		request.setBookId(reservation.getBook().getId());
		request.setNotes("Assign Booked by Admin");

		bookLoanService.checkoutBookForUser(reservation.getUser().getId(), request);

		return reservationMapper.toDTO(savedReservation);
	}

	@Override
	public PageResponse<ReservationDTO> getMyReservations(ReservationSearchRequest searchRequest) {


		return null;
	}

	@Override
	public PageResponse<ReservationDTO> searchReservations(ReservationSearchRequest searchRequest) {

		Pageable pageable = createPageable(searchRequest);

		Page<Reservation> reservationPage =
						reservationRepo.searchReservationsWithFilters(
										searchRequest.getUserId(),
										searchRequest.getBookId(),
										searchRequest.getStatus(),
										searchRequest.getActiveOnly() != null
											 ? searchRequest.getActiveOnly() : false,
										pageable
						) ;

		return buildPageResponse(reservationPage);
	}

	private PageResponse<ReservationDTO> buildPageResponse(Page<Reservation> reservationPage) {

		List<ReservationDTO> dtos = reservationPage.getContent().stream()
						.map(reservationMapper::toDTO)
						.toList();

		PageResponse<ReservationDTO> response = new PageResponse<>();
		response.setContent(dtos);
		response.setPageNumber(reservationPage.getNumber());
		response.setPageSize(reservationPage.getSize());
		response.setTotalPages(reservationPage.getTotalPages());
		response.setTotalElements(reservationPage.getTotalElements());
		response.setLast(reservationPage.isLast());

		return response;

	}

	private Pageable createPageable(ReservationSearchRequest searchRequest) {

		Sort sort = "ASC".equalsIgnoreCase(searchRequest.getSortDirection())
						? Sort.by(searchRequest.getSortBy()).ascending()
						: Sort.by(searchRequest.getSortBy()).descending();

		return PageRequest.of(searchRequest.getPage(), searchRequest.getSize(),
						sort);
	}
}
