package com.ok.model;

import com.ok.domain.BookLoanStatus;
import com.ok.domain.BookLoanType;
import com.ok.payload.dto.PaymentDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Book book;

	private BookLoanType type;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookLoanStatus status;

	@Column(nullable = false)
	private LocalDate checkoutDate;

	private LocalDate dueDate;

	private LocalDate returnDate;

	@Column(nullable = false)
	private Integer renewalCount;

	@Column(nullable = false)
	private Integer maxRenewals = 2;

	// fine todo

	@Column(length = 500)
	private String notes;

	@Column(nullable = false)
	private Boolean isOverdue = false;

	@Column(nullable = false)
	private Integer overdueDays = 0;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public boolean isActive() {
		return status == BookLoanStatus.CHECKED_OUT ||
						status == BookLoanStatus.OVERDUE;
	}

	public boolean canRenew() {
		return status == BookLoanStatus.CHECKED_OUT
						&& !isOverdue
						&& renewalCount < maxRenewals;
	}
}
