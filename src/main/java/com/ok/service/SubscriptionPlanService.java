package com.ok.service;

import com.ok.model.SubscriptionPlan;
import com.ok.payload.dto.SubscriptionPlanDTO;

public interface SubscriptionPlanService {

	SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO);
}
