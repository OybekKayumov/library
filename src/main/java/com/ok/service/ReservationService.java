package com.ok.service;

import com.ok.payload.dto.ReservationDTO;

public interface ReservationService {

	ReservationDTO createReservation(ReservationRequest reservationRequest);

}
