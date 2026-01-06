package com.ok.mapper;

import com.ok.model.Payment;
import com.ok.payload.dto.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

	public PaymentDTO toDTO(Payment payment) {

		if (payment == null) {
			return null;
		}

		PaymentDTO dto = new PaymentDTO();

		dto.setId(payment.getId());

		if (payment.getUser() != null) {
			dto.setUserName(payment.getUser().getFullName());
			dto.setUserEmail(payment.getUser().getEmail());
		}

		if (payment.getSubscription() != null) {
			dto.setSubscriptionId(payment.getSubscription().getId());
		}

		dto.setPaymentType(payment.getPaymentType());
		dto.setStatus(payment.getStatus());
		dto.setGateway(payment.getGateway());
		dto.setAmount(payment.getAmount());
		dto.setTransactionId(payment.getTransactionId());
		dto.setGatewayPaymentId(payment.getGatewayPaymentId());
		dto.setGatewayOrderId(payment.getGatewayOrderId());
		dto.setGatewaySignature(payment.getGatewaySignature());

		dto.setDescription(payment.getDescription());
		dto.setFailureReason(payment.getFailureReason());

		dto.setInitiatedAt(payment.getInitiatedAt());
		dto.setCompletedAt(payment.getCompletedAt());
		dto.setCreatedAt(payment.getCreatedAt());
		dto.setUpdatedAt(payment.getUpdatedAt());

		return dto;
	}


}
