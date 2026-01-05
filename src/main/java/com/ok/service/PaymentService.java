package com.ok.service;

import com.ok.payload.request.PaymentInitiateRequest;
import com.ok.payload.response.PaymentInitiateResponse;

public interface PaymentService {

	PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req);
}
