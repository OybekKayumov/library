package com.ok.repo;

import com.ok.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

	@Query("select s from Subscription s where s.user.id = :userId AND " +
					"s.isActive = true AND " +
					"s.startDate >= :today AND s.endDate >= :today")
	Optional<Subscription> findActiveSubscriptionByUserId(
					@Param("userId") Long userId,
					@Param("today") LocalDate today);

	@Query("select s from Subscription s where s.isActive=true AND " +
					" s.endDate < :today")
	List<Subscription> findExpiredActiveSubscriptions(
					@Param("today") LocalDate today);

}
