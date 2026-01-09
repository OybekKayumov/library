package com.ok.service;

import com.ok.payload.dto.ReservationDTO;
import com.ok.payload.request.ReservationRequest;
import com.ok.payload.request.ReservationSearchRequest;
import com.ok.payload.response.PageResponse;

public interface ReservationService {

	ReservationDTO createReservation(ReservationRequest reservationRequest) throws Exception;

	ReservationDTO createReservationForUser(
					ReservationRequest reservationRequest, Long userId) throws Exception;

	ReservationDTO cancelReservation(Long reservationId) throws Exception;

	ReservationDTO fulfillReservation(Long reservationId) throws Exception;

	PageResponse<ReservationDTO> getMyReservations(
					ReservationSearchRequest searchRequest) throws Exception;

	PageResponse<ReservationDTO> searchReservations(
					ReservationSearchRequest searchRequest);

}
