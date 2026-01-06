package com.ok.event.listener;

import com.ok.exception.SubscriptionException;
import com.ok.model.Payment;
import com.ok.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.ok.domain.PaymentType.*;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

	private final SubscriptionService subscriptionService;

	@Async
	@EventListener
	@Transactional
	public void handlePaymentSuccess(Payment payment) throws SubscriptionException {

		switch (payment.getPaymentType()) {

			case FINE:
			case LOST_BOOK_PENALTY:
			case DAMAGED_BOOK_PENALTY:
				break;

			case MEMBERSHIP:
				subscriptionService.activateSubscription(payment.getSubscription().getId(),
								payment.getId());


		}
	}
}
