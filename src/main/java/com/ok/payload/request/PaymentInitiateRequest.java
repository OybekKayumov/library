package com.ok.payload.request;

import com.ok.domain.PaymentGateway;
import com.ok.domain.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInitiateRequest {

	@NotNull(message = "User ID is mandatory!")
	private Long userId;

	private Long bookLoanId;

	@NotNull(message = "Payment type is mandatory!")
	private PaymentType paymentType;

	@NotNull(message = "Payment Gateway is mandatory!")
	private PaymentGateway gateway;

	@NotNull(message = "Amount is mandatory!")
	@Positive(message = "Amount must be positive!")
	private Long amount;

	@Size(max = 500, message = "Description must not exceed 500 characters")
	private String description;

	private Long fineId;
	private Long subscriptionId;

	@Size(max = 500, message = "Success URL must not exceed 500 characters")
	private String successUrl;

	@Size(max = 500, message = "Cancel URL must not exceed 500 characters")
	private String cancelUrl;
}
