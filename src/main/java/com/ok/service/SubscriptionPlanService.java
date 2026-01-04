package com.ok.service;

import com.ok.model.SubscriptionPlan;
import com.ok.payload.dto.SubscriptionPlanDTO;

import java.util.List;

public interface SubscriptionPlanService {

	SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO);

	SubscriptionPlanDTO updateSubscriptionPlan(Long planId,
	                                           SubscriptionPlanDTO planDTO);

	void deleteSubscriptionPlan(Long planId);

	List<SubscriptionPlanDTO> getAllSubscriptionPlan();
}
