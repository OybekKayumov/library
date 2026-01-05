package com.ok.service;

import com.ok.payload.dto.SubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {

	SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO);

	SubscriptionDTO getUsersActiveSubscriptions(Long userId);

	SubscriptionDTO cancelSubscription(Long subscriptionId, String reason);

	SubscriptionDTO acceptSubscription(Long subscriptionId, Long paymentId);

	List<SubscriptionDTO> getAllSubscriptions(Pageable pageable);

}
