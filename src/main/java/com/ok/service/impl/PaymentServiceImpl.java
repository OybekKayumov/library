package com.ok.service.impl;

import com.ok.domain.PaymentGateway;
import com.ok.domain.PaymentStatus;
import com.ok.model.Payment;
import com.ok.model.Subscription;
import com.ok.model.User;
import com.ok.payload.dto.PaymentDTO;
import com.ok.payload.request.PaymentInitiateRequest;
import com.ok.payload.request.PaymentVerifyRequest;
import com.ok.payload.response.PaymentInitiateResponse;
import com.ok.payload.response.PaymentLinkResponse;
import com.ok.repo.PaymentRepo;
import com.ok.repo.SubscriptionRepo;
import com.ok.repo.UserRepo;
import com.ok.service.PaymentService;
import com.ok.service.gateway.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


	private final UserRepo userRepo;
	private final SubscriptionRepo subscriptionRepo;
	private final PaymentRepo paymentRepo;
	private final RazorpayService razorpayService;

	@Override
	public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws Exception {

		User user = userRepo.findById(request.getUserId()).get();

		Payment payment = new Payment();
		payment.setUser(user);
		payment.setPaymentType(request.getPaymentType());
		payment.setGateway(request.getGateway());
		payment.setAmount(request.getAmount());
		payment.setDescription(request.getDescription());
		payment.setStatus(PaymentStatus.PENDING);
		payment.setTransactionId("TXN_" + UUID.randomUUID());
		payment.setInitiatedAt(LocalDateTime.now());

		if (request.getSubscriptionId() != null) {

			Subscription sub = subscriptionRepo
							.findById(request.getSubscriptionId())
							.orElseThrow(() -> new Exception("Subscription not found!"));

			payment.setSubscription(sub);
		}

		payment = paymentRepo.save(payment);

		PaymentInitiateResponse response =  new PaymentInitiateResponse();

		if (request.getGateway() == PaymentGateway.RAZORPAY) {

			PaymentLinkResponse paymentLinkResponse = razorpayService.createPaymentLink(
							user, payment
			);

			response = PaymentInitiateResponse.builder()
							.paymentId(payment.getId())
							.gateway(payment.getGateway())
							.checkoutUrl(paymentLinkResponse.getPayment_link_url())
							.transactionId(paymentLinkResponse.getPayment_link_id())
							.amount(payment.getAmount())
							.description(payment.getDescription())
							.success(true)
							.message("Payment initiated successfully!")
							.build();

			payment.setGatewayOrderId(paymentLinkResponse.getPayment_link_id());
		}

		payment.setStatus(PaymentStatus.PROCESSING);
		paymentRepo.save(payment);

		//* payment initiate event

		return response;
	}

	@Override
	public PaymentDTO verifyPayment(PaymentVerifyRequest req) {
		return null;
	}

	@Override
	public Page<PaymentDTO> getAllPayments(Pageable pageable) {
		return null;
	}
}
