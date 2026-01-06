package com.ok.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RenewalRequest {

	@NotNull(message = "Book loan ID is mandatory!")
	private Long bookLoanId;

	@Min(value = 1, message = "Extension days must be at least 1")
	private Integer extensionDays = 14;

	private String notes;

}
