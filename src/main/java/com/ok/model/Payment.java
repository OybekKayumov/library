package com.ok.model;

import com.ok.domain.PaymentGateway;
import com.ok.domain.PaymentStatus;
import com.ok.domain.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Subscription subscription;

	private PaymentType paymentType;

	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	private PaymentGateway gateway;

	private Long amount;

	private String transactionId;
	private String gatewayPaymentId;
	private String gatewayOrderId;
	private String gatewaySignature;

	private String description;
	private String failureReason;

	@CreationTimestamp
	private LocalDateTime initiatedAt;
	private LocalDateTime completedAt;

	@UpdateTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
