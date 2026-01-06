package com.ok.service.impl;

import com.ok.domain.PaymentGateway;
import com.ok.domain.PaymentType;
import com.ok.exception.SubscriptionException;
import com.ok.mapper.SubscriptionMapper;
import com.ok.model.Subscription;
import com.ok.model.SubscriptionPlan;
import com.ok.model.User;
import com.ok.payload.dto.SubscriptionDTO;
import com.ok.payload.request.PaymentInitiateRequest;
import com.ok.payload.response.PaymentInitiateResponse;
import com.ok.repo.SubscriptionPlanRepo;
import com.ok.repo.SubscriptionRepo;
import com.ok.service.PaymentService;
import com.ok.service.SubscriptionService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

	private final SubscriptionRepo subscriptionRepo;
	private final SubscriptionMapper subscriptionMapper;
	private final UserService userService;
	private final SubscriptionPlanRepo subscriptionPlanRepo;
	private final PaymentService paymentService;

	@Override
	//public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws
	// Exception {
	public PaymentInitiateResponse subscribe(SubscriptionDTO subscriptionDTO) throws Exception {

		User user = userService.getCurrentUser();

		SubscriptionPlan plan = subscriptionPlanRepo
						.findById(subscriptionDTO.getPlanId()).orElseThrow(
								() -> new Exception("Plan not found!")
		);

		Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO, plan, user);
		subscription.initializeFromPlan();
		subscription.setIsActive(false);
		Subscription savedSubscription = subscriptionRepo.save(subscription);

		// create payment (todo)
		PaymentInitiateRequest paymentInitiateRequest =
						PaymentInitiateRequest.builder()
										.userId(user.getId())
										.subscriptionId(savedSubscription.getId())
										.paymentType(PaymentType.MEMBERSHIP)
										.gateway(PaymentGateway.RAZORPAY)
										.amount(subscription.getPrice())
										.description("Library Subscription - " + plan.getName())
										.build();

		//return subscriptionMapper.toDTO(savedSubscription);
		return paymentService.initiatePayment(paymentInitiateRequest);
	}

	@Override
	public SubscriptionDTO getUsersActiveSubscriptions(Long userId) throws Exception {

		User user = userService.getCurrentUser();

		Subscription subscription = subscriptionRepo
										.findActiveSubscriptionByUserId(user.getId(),	LocalDate.now())
										.orElseThrow(() -> new SubscriptionException(
														"No active subscription found!"));

		return subscriptionMapper.toDTO(subscription);
	}

	@Override
	public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException {

		Subscription subscription = subscriptionRepo.findById(subscriptionId)
						.orElseThrow(() -> new SubscriptionException(
										"Subscription with ID " + subscriptionId + " not found!"
						));

		if (!subscription.getIsActive()) {

			throw new SubscriptionException("Subscription is already inactive!");
		}

		subscription.setIsActive(false);
		subscription.setCancelledAt(LocalDateTime.now());
		subscription.setCancellationReason(
						reason != null ? reason : "Cancelled by user");

		subscription =  subscriptionRepo.save(subscription);

		return subscriptionMapper.toDTO(subscription);
	}

	@Override
	public SubscriptionDTO activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException {

		Subscription subscription = subscriptionRepo.findById(subscriptionId)
						.orElseThrow(
							() -> new SubscriptionException("Subscription not find by ID!")
						);

		// verify payment (todo)

		subscription.setIsActive(true);
//		subscription.setStartDate(LocalDate.now());
//		subscription.calculateEndDate();

		subscription = subscriptionRepo.save(subscription);

		return subscriptionMapper.toDTO(subscription);
	}

	@Override
	public List<SubscriptionDTO> getAllSubscriptions(Pageable pageable) {

		List<Subscription> subscriptions = subscriptionRepo.findAll();

		return subscriptionMapper.toDTOList(subscriptions);
	}

	@Override
	public void deactivateExpiredSubscriptions() throws Exception {

		List<Subscription> expiredSubscriptions =
						subscriptionRepo.findExpiredActiveSubscriptions(LocalDate.now());

		for (Subscription subscription : expiredSubscriptions) {
			subscription.setIsActive(false);
			subscriptionRepo.save(subscription);
		}

	}
}
