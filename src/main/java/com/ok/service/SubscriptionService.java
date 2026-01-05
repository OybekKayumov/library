package com.ok.service;

import com.ok.exception.SubscriptionException;
import com.ok.payload.dto.SubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {

	SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception;

	SubscriptionDTO getUsersActiveSubscriptions(Long userId) throws Exception;

	SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException;

	SubscriptionDTO acceptSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException;

	List<SubscriptionDTO> getAllSubscriptions(Pageable pageable);

	void deactivateExpiredSubscriptions(Long userId) throws Exception;

}
