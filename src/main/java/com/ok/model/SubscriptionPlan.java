package com.ok.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String planCode;

	@Column(nullable = false, length = 100)
	private String name;

	//@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Integer durationDays;

	@Column(nullable = false)
	private Long price;

	private String currency="UZS";

	@Column(nullable = false)
	@Positive(message = "Max Books must be positive!")
	private Integer maxBooksAllowed;

	@Column(nullable = false)
	@Positive(message = "Max Days must be positive!")
	private Integer maxDaysPerBook;

	private Integer displayOrder = 0;

	private Boolean isActive = true;

	private Boolean isFeatured = false;

	private String badgeText;

	private String adminNotes;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private String createdBy;

	private String updatedBy;

}

