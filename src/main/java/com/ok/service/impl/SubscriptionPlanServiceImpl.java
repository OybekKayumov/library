package com.ok.service.impl;

import com.ok.mapper.SubscriptionPlanMapper;
import com.ok.model.SubscriptionPlan;
import com.ok.model.User;
import com.ok.payload.dto.SubscriptionPlanDTO;
import com.ok.repo.SubscriptionPlanRepo;
import com.ok.service.SubscriptionPlanService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {


	private final SubscriptionPlanRepo planRepo;
	private final SubscriptionPlanMapper planMapper;

	private final UserService userService;

	@Override
	public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception {

		if (planRepo.existsByPlanCode(planDTO.getPlanCode())) {

			throw new Exception("Plan code already exists");
		}

		SubscriptionPlan plan = planMapper.toEntity(planDTO);

		User currentUser = userService.getCurrentUser();
		plan.setCreatedBy(currentUser.getFullName());
		plan.setUpdatedBy(currentUser.getFullName());

		SubscriptionPlan savedPlan = planRepo.save(plan);

		return planMapper.toDTO(savedPlan);
	}

	@Override
	public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws Exception {

		SubscriptionPlan existingPlan = planRepo.findById(planId).orElseThrow(
						() -> new Exception("Plan not found!"));

		planMapper.updateEntity(existingPlan, planDTO);

		User currentUser = userService.getCurrentUser();
		existingPlan.setUpdatedBy(currentUser.getFullName());

		SubscriptionPlan updatedPlan = planRepo.save(existingPlan);

		return planMapper.toDTO(updatedPlan);
	}

	@Override
	public void deleteSubscriptionPlan(Long planId) throws Exception {

		SubscriptionPlan existingPlan = planRepo.findById(planId).orElseThrow(
						() -> new Exception("Plan not found!"));

		planRepo.delete(existingPlan);
	}

	@Override
	public List<SubscriptionPlanDTO> getAllSubscriptionPlan() {

		List<SubscriptionPlan> planList = planRepo.findAll();

		return planList.stream().map(
						planMapper::toDTO
		).collect(Collectors.toList());
	}
}
