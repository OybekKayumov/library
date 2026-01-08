package com.ok.mapper;

import com.ok.model.Reservation;
import com.ok.payload.dto.ReservationDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ReservationMapper {

	public ReservationDTO toDTO(Reservation reservation) {

		if(reservation == null) {
			return null;
		}

		ReservationDTO dto = new ReservationDTO();
		dto.setId(reservation.getId());
		if (reservation.getUser() != null) {
			dto.setUserId(reservation.getUser().getId());
			dto.setUserName(reservation.getUser().getFullName());
			dto.setUserEmail(reservation.getUser().getEmail());
		}

		if (reservation.getBook() != null) {
			dto.setBookId(reservation.getBook().getId());
			dto.setBookTitle(reservation.getBook().getTitle());
			dto.setBookIsbn(reservation.getBook().getIsbn());
			dto.setBookAuthor(reservation.getBook().getAuthor());
			dto.setIsBookAvailable(reservation.getBook().getAvailableCopies() > 0);
		}

		dto.setStatus(reservation.getStatus());
		dto.setReservedAt(reservation.getReservedAt());
		dto.setAvailableAt(reservation.getAvailableAt());
		dto.setAvailableUntil(reservation.getAvailableUntil());
		dto.setFulfilledAt(reservation.getFulfilledAt());
		dto.setCancelledAt(reservation.getCancelledAt());
		dto.setQueuePosition(reservation.getQueuePosition());
		dto.setNotificationSent(reservation.getNotificationSent());
		dto.setNotes(reservation.getNotes());
		dto.setCreatedAt(reservation.getCreatedAt());
		dto.setUpdatedAt(reservation.getUpdatedAt());

		dto.setExpired(reservation.hasExpired());
		dto.setCanBeCancelled(reservation.canBeCancelled());

		if (reservation.getAvailableUntil() != null) {

			LocalDateTime now = LocalDateTime.now();

			if (now.isBefore(reservation.getAvailableUntil())) {

				long hours = Duration.between(now, reservation.getAvailableUntil()).toHours();
				dto.setHoursUntilExpiry(hours);

			} else {
				dto.setHoursUntilExpiry(0L);
			}
		}

		return dto;

	}

}
