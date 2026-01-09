package com.ok.payload.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReviewRequest {

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	private Integer rating;

	@NotBlank
	@Size(min = 10, max = 2000)
	private String reviewText;

	@Size(max = 200)
	private String title;
}
