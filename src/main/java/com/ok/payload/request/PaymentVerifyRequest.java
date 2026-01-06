package com.ok.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerifyRequest {

	private String razorpayPaymentId;
//	private String razorpayOrderId;
//	private String razorpaySignature;

	private String stripePaymentIntentId;
	private String stripePaymentIntentStatus;

}
