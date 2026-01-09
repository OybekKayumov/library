package com.ok.model;

import com.ok.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static com.ok.domain.ReservationStatus.PENDING;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Book book;

	@Enumerated(EnumType.STRING)
	private ReservationStatus status = PENDING;

	private LocalDateTime reservedAt;
	private LocalDateTime availableAt;
	private LocalDateTime availableUntil;

	@Column(name = "fulfilled_at")
	private LocalDateTime fulfilledAt;

	@Column(name = "cancelled_at")
	private LocalDateTime cancelledAt;

	@Column(name = "queue_position")
	private Integer queuePosition;

	@Column(name = "notofocation_sent", nullable = false)
	private Boolean notificationSent = false;

	@Column(columnDefinition = "TEXT")
	private String notes;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false,  updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public boolean canBeCancelled() {

		return status == PENDING || status == ReservationStatus.AVAILABLE;
	}

	public boolean hasExpired() {
		return status == ReservationStatus.AVAILABLE
						&& availableUntil != null
						&& LocalDateTime.now().isAfter(availableUntil);
	}
}
