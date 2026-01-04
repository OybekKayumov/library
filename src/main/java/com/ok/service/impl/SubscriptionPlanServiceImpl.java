package com.ok.service.impl;

import com.ok.payload.dto.SubscriptionPlanDTO;
import com.ok.service.SubscriptionPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {


	@Override
	public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) {
		return null;
	}

	@Override
	public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) {
		return null;
	}

	@Override
	public void deleteSubscriptionPlan(Long planId) {

	}

	@Override
	public List<SubscriptionPlanDTO> getAllSubscriptionPlan() {
		return List.of();
	}
}
