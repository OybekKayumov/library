package com.ok.controller;

import com.ok.payload.dto.ReservationDTO;
import com.ok.payload.request.ReservationRequest;
import com.ok.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ResponseEntity<?> createReservation(
					@Valid @RequestBody ReservationRequest reservationRequest) throws Exception {

		ReservationDTO reservationDTO =
						reservationService.createReservation(reservationRequest);

		return ResponseEntity.ok(reservationDTO);
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<?> createReservationForUser(
					@PathVariable Long userId,
					@Valid @RequestBody ReservationRequest reservationRequest) throws Exception {

		ReservationDTO reservation =
						reservationService.createReservationForUser(
										reservationRequest, userId);

		return new ResponseEntity<>(reservation, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable Long id) throws Exception {

		ReservationDTO reservation = reservationService.cancelReservation(id);

		return ResponseEntity.ok(reservation);
	}

	@PostMapping("/{id}/fulfill")
	public ResponseEntity<?> fulfillReservation(
					@PathVariable Long id) throws Exception {

		ReservationDTO reservation =
						reservationService.fulfillReservation(id);

		return ResponseEntity.ok(reservation);
	}
}
