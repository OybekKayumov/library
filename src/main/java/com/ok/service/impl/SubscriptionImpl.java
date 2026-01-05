package com.ok.service.impl;

import com.ok.exception.SubscriptionException;
import com.ok.mapper.SubscriptionMapper;
import com.ok.model.Subscription;
import com.ok.model.SubscriptionPlan;
import com.ok.model.User;
import com.ok.payload.dto.SubscriptionDTO;
import com.ok.repo.SubscriptionPlanRepo;
import com.ok.repo.SubscriptionRepo;
import com.ok.service.SubscriptionService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

	private final SubscriptionRepo subscriptionRepo;
	private final SubscriptionMapper subscriptionMapper;
	private final UserService userService;
	private final SubscriptionPlanRepo subscriptionPlanRepo;

	@Override
	public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception {

		User user = userService.getCurrentUser();

		SubscriptionPlan plan = subscriptionPlanRepo
						.findById(subscriptionDTO.getPlanId()).orElseThrow(
								() -> new Exception("Plan not found!")
		);

//		Optional<Subscription> subscription = subscriptionRepo

		Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);
		subscription.initializeFromPlan();
		subscription.setIsActive(false);
		Subscription savedSubscription = subscriptionRepo.save(subscription);

		// create payment (todo)

		return subscriptionMapper.toDTO(savedSubscription);
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
