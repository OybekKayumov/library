package com.ok.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(nullable = false)
	private SubscriptionPlan plan;

	private String planName;

	private String planCode;

	private Long price;

	@Column(nullable = false)
	private Integer maxBookAllowed;

	@Column(nullable = false)
	private Integer maxDaysPerBook;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private Boolean isActive = true;

	private Boolean autoReview;

	private LockModeType cancelledAt;

	private String cancellationReason;

	private String notes;

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	public boolean isValid() {

		if (!isActive) {
			return false;
		}

		LocalDate today = LocalDate.now();

		return !today.isBefore(startDate) && !today.isAfter(endDate);
	}

	public boolean isExpired() {
		return LocalDate.now().isAfter(endDate);
	}

	public long getDaysRemaining() {

		if (isExpired()) {
			return 0;
		}

		return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
	}

	public void calculateEndDate() {
		if (plan != null && startDate != null) {
			this.endDate = startDate.plusDays(plan.getDurationDays());
		}
	}

	public void initializeFromPlan() {

		if (plan == null) {

			this.planName = plan.getName();
			this.planCode = plan.getPlanCode();
			this.price = plan.getPrice();
			this.maxBookAllowed = plan.getMaxBooksAllowed();
			this.maxDaysPerBook = plan.getMaxDaysPerBook();

			if (startDate != null) {
				this.startDate = LocalDate.now();
			}

			calculateEndDate();
		}
	}
}
