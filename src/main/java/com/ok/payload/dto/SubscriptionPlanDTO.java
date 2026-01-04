package com.ok.payload.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlanDTO {

	private Long id;

	@NotBlank(message = "Plan code is mandatory!")
	private String planCode;

	@NotBlank(message = "Plan name is mandatory!")
	private String name;

	private String description;

	@NotNull(message = "Duration is mandatory!")
	@Positive(message = "Duration must be positive!")
	private Integer durationDays;

	@NotNull(message = "Price is mandatory!")
	@Positive(message = "Price must be positive!")
	private Integer price;

	private String currency;

	@NotNull(message = "Max books allowed is mandatory!")
	@Positive(message = "Max books must be positive!")
	private Integer maxBooksAllowed;

	@NotNull(message = "Max days per book is mandatory!")
	@Positive(message = "Max days per book must be positive!")
	private Integer maxDaysPerBook;

	private Integer displayOrder;
	private Boolean isActive;
	private Boolean isFeatured;
	private String badgeText;
	private String adminNotes;

//	private Double priceInMajorUnits;
//	private Double monthlyEquivalentPrice;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private String createdBy;
	private String updatedBy;


}
