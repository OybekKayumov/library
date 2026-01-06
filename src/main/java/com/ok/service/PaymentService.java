package com.ok.service;

import com.ok.payload.dto.PaymentDTO;
import com.ok.payload.request.PaymentInitiateRequest;
import com.ok.payload.request.PaymentVerifyRequest;
import com.ok.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

	PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) throws Exception;

	PaymentDTO verifyPayment(PaymentVerifyRequest req) throws Exception;

	Page<PaymentDTO> getAllPayments(Pageable pageable);

}
