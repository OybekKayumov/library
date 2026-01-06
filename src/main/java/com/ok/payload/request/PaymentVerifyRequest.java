package com.ok.payload.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentVerifyRequest {

	private String razorpayPaymentId;
//	private String razorpayOrderId;
//	private String razorpaySignature;

	private String stripePaymentIntentId;
	private String stripePaymentIntentStatus;

}
