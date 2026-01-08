package com.ok.service;

import com.ok.payload.dto.ReservationDTO;
import com.ok.payload.request.ReservationRequest;
import com.ok.payload.request.ReservationSearchRequest;
import com.ok.payload.response.PageResponse;

public interface ReservationService {

	ReservationDTO createReservation(ReservationRequest reservationRequest);

	ReservationDTO createReservationForUser(
					ReservationRequest reservationRequest, Long userId) throws Exception;

	ReservationDTO cancelReservation(Long reservationId);

	ReservationDTO fulfillReservation(Long reservationId);

	PageResponse<ReservationDTO> getMyReservations(
					ReservationSearchRequest searchRequest);

	PageResponse<ReservationDTO> searchReservations(
					ReservationSearchRequest searchRequest);

}
