package com.ok.payload.request;

import com.ok.domain.FineType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaiveFineRequest {

	@NotNull(message = "Fine ID is mandatory!")
	private Long fineId;

	@NotNull(message = "Waiver reason is mandatory!")
	private FineType reason;

}
