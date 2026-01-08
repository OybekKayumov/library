package com.ok.model;

import com.ok.domain.FineStatus;
import com.ok.domain.FineType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(nullable = false)
	private BookLoan bookLoan;

	private FineType type;

	@Column(nullable=false)
	private Long amount;

	private FineStatus status;

	@Column(length = 500)
	private String reason;

	@Column(length = 1000)
	private String notes;

	@ManyToOne
	private User waivedBy;

	@Column(name = "waived_at")
	private LocalDateTime waivedAt;

	@Column(name = "waiver_reason", length = 500)
	private String waiverReason;

	@Column(name = "paid_at")
	private LocalDateTime paidAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processed_by_user_id")
	private User processedBy;

	@Column(name = "transaction_id", length = 100)
	private String transactionId;

	@Column(nullable=false, updatable=false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable=false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;




}
