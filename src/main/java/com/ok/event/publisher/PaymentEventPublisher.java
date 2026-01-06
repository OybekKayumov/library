package com.ok.event.publisher;

import com.ok.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void publishPaymentSuccessEvent(Payment payment) {

		applicationEventPublisher.publishEvent(payment);
	}

}
