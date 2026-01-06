package com.ok.service;

import com.ok.payload.dto.PaymentDTO;
import com.ok.payload.request.PaymentInitiateRequest;
import com.ok.payload.request.PaymentVerifyRequest;
import com.ok.payload.response.PaymentInitiateResponse;

public interface PaymentService {

	PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req);

	PaymentDTO verifyPayment(PaymentVerifyRequest req);
}
