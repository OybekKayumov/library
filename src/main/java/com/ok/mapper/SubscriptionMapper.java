package com.ok.mapper;

import com.ok.exception.SubscriptionException;
import com.ok.model.Subscription;
import com.ok.model.SubscriptionPlan;
import com.ok.model.User;
import com.ok.payload.dto.SubscriptionDTO;
import com.ok.repo.SubscriptionPlanRepo;
import com.ok.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {

	private final UserRepo userRepo;
	private final SubscriptionPlanRepo planRepo;

	public SubscriptionDTO toDTO(Subscription subscription) {

		if (subscription == null) {
			return null;
		}

		SubscriptionDTO dto = new SubscriptionDTO();
		dto.setId(subscription.getId());

		if (subscription.getUser() != null) {

			dto.setUserId(subscription.getUser().getId());
			dto.setUserName(subscription.getUser().getFullName());
			dto.setUserEmail(subscription.getUser().getEmail());
		}

		if (subscription.getPlan() != null) {
			dto.setPlanId(subscription.getPlan().getId());
		}

		dto.setPlanName(subscription.getPlan().getName());
		dto.setPlanCode(subscription.getPlanCode());
		dto.setPrice(subscription.getPrice());

		dto.setStartDate(subscription.getStartDate());
		dto.setEndDate(subscription.getEndDate());
		dto.setIsActive(subscription.getIsActive());
		dto.setMaxBookAllowed(subscription.getMaxBookAllowed());
		dto.setMaxDaysPerBook(subscription.getMaxDaysPerBook());
		dto.setAutoRenew(subscription.getAutoRenew());
		dto.setCancelledAt(subscription.getCancelledAt());
		dto.setCancellationReason(subscription.getCancellationReason());
		dto.setNotes(subscription.getNotes());
		dto.setCreatedAt(subscription.getCreatedAt());
		dto.setUpdatedAt(subscription.getUpdatedAt());

		dto.setDaysRemaining(subscription.getDaysRemaining());
		dto.setIsValid(subscription.isValid());
		dto.setIsExpired(subscription.isExpired());

		return dto;
	}

	public Subscription toEntity(SubscriptionDTO dto) throws SubscriptionException {

		if (dto == null) {
			return null;
		}

		Subscription subscription = new Subscription();
		subscription.setId(dto.getId());

		if (dto.getUserId() != null) {

			User user = userRepo.findById(dto.getUserId())
							.orElseThrow(() -> new SubscriptionException("User not found with id: " + dto.getUserId() ));

			subscription.setUser(user);
		}

		if (dto.getPlanId() != null) {

			SubscriptionPlan plan = planRepo.findById(dto.getPlanId())
							.orElseThrow(() -> new SubscriptionException("Plan not found with id: " + dto.getPlanId() ));

			subscription.setPlan(plan);
		}

		subscription.setPlanName(dto.getPlanName());
		subscription.setPlanCode(dto.getPlanCode());
		subscription.setPrice(dto.getPrice());
		subscription.setStartDate(dto.getStartDate());
		subscription.setEndDate(dto.getEndDate());
		subscription.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : false);
		subscription.setMaxBookAllowed(dto.getMaxBookAllowed());
		subscription.setMaxDaysPerBook(dto.getMaxDaysPerBook());
		subscription.setAutoRenew(dto.getAutoRenew()  != null ? dto.getAutoRenew() : false);
		subscription.setCancelledAt(dto.getCancelledAt());
		subscription.setCancellationReason(dto.getCancellationReason());
		subscription.setNotes(dto.getNotes());

		return subscription;
	}

	public List<SubscriptionDTO> toDTOList(List<Subscription> subscriptions) {

		if (subscriptions == null) {
			return null;
		}

		return subscriptions.stream()
						.map(this::toDTO)
						.collect(Collectors.toList());
	}


}
