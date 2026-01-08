package com.ok.payload.dto;

import com.ok.domain.FineStatus;
import com.ok.domain.FineType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FineDTO {

	private Long id;

	@NotNull(message = "Book loan is mandatory!")
	private Long bookLoanId;

	private String bookTitle;
	private String bookIsbn;

	@NotNull(message = "User ID is mandatory!")
	private Long userId;

	private String userName;

	private String userEmail;

	@NotNull(message = "Fine type is mandatory!")
	private FineType type;

	@NotNull(message = "Fine amount is mandatory!")
	@PositiveOrZero(message = "Fine amount cannot be negative!")
	private Long amount;

	@PositiveOrZero(message = "Amount paid cannot be negative!")
	private Long amountPaid;

	private Long amountOutstanding;

	@NotNull(message = "Fine status is mandatory!")
	private FineStatus status;

	private String reason;

	private String notes;

	private Long waivedByUserId;

	private String waivedByUserName;

	private LocalDateTime waivedAt;

	private String waiverReason;

	private LocalDateTime paidAt;

	private Long processedByUserId;
	private String processedByUserName;

	private String transactionId;

	private LocalDateTime closedAt;
	private LocalDateTime updatedAt;



}
