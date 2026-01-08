package com.ok.service.impl;

import com.ok.domain.FineStatus;
import com.ok.domain.FineType;
import com.ok.domain.PaymentGateway;
import com.ok.domain.PaymentType;
import com.ok.mapper.FineMapper;
import com.ok.model.BookLoan;
import com.ok.model.Fine;
import com.ok.model.User;
import com.ok.payload.dto.FineDTO;
import com.ok.payload.request.CreateFineRequest;
import com.ok.payload.request.PaymentInitiateRequest;
import com.ok.payload.request.WaiveFineRequest;
import com.ok.payload.response.PageResponse;
import com.ok.payload.response.PaymentInitiateResponse;
import com.ok.repo.BookLoanRepo;
import com.ok.repo.FineRepo;
import com.ok.service.FineService;
import com.ok.service.PaymentService;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {
	private final BookLoanRepo bookLoanRepo;
	private final FineRepo fineRepo;
	private final FineMapper fineMapper;
	private final UserService userService;
	private final PaymentService paymentService;

	@Override
	public FineDTO createFine(CreateFineRequest createFineRequest) throws Exception {

		BookLoan bookLoan = bookLoanRepo.findById(createFineRequest.getBookLoanId())
						.orElseThrow(() -> new Exception("Book Loan doesn't exist"));

		Fine fine = Fine.builder()
						.bookLoan(bookLoan)
						.user(bookLoan.getUser())
						.type(createFineRequest.getType())
						.status(FineStatus.PENDING)
						.reason(createFineRequest.getReason())
						.notes(createFineRequest.getNotes())
						.build();

		Fine savedFine = fineRepo.save(fine);

		return fineMapper.toDTO(savedFine);

	}

	@Override
	public PaymentInitiateResponse payFine(Long fineId, String transactionId) throws Exception {

		Fine fine = fineRepo.findById(fineId)
						.orElseThrow(() -> new Exception("Fine doesn't exist"));


		if (fine.getStatus().equals(FineStatus.PAID)) {
			throw new Exception("Fine is already paid");
		}

		if (fine.getStatus().equals(FineStatus.WAIVED)) {
			throw new Exception("Fine waived");
		}

		User user = userService.getCurrentUser();

		PaymentInitiateRequest request = PaymentInitiateRequest.builder()
						.userId(user.getId())
						.fineId(fine.getId())
						.paymentType(PaymentType.FINE)
						.gateway(PaymentGateway.RAZORPAY)
						.amount(fine.getAmount())
						.description("Library fine payment")
						.build();

		return paymentService.initiatePayment(request);
	}

	@Override
	public void markFineAsPaid(Long fineId, Long amount, String transactionId) throws Exception {

		Fine fine = fineRepo.findById(fineId)
						.orElseThrow(() -> new Exception("Fine not found with id: " + fineId));

		fine.applyPayment(amount);
		fine.setTransactionId(transactionId);
		fine.setStatus(FineStatus.PAID);
		fine.setUpdatedAt(LocalDateTime.now());

		fineRepo.save(fine);

	}

	@Override
	public FineDTO waiveFine(WaiveFineRequest waiveFineRequest) throws Exception {

		Fine fine = fineRepo.findById(waiveFineRequest.getFineId())
						.orElseThrow(() -> new Exception("Fine not found with id"));

		if (fine.getStatus().equals(FineStatus.WAIVED)) {
			throw new Exception("Fine is already waived");
		}

		if (fine.getStatus().equals(FineStatus.PAID)) {
			throw new Exception("Fine is already paid");
		}

		User currentAdmin = userService.getCurrentUser();
		fine.waive(currentAdmin, waiveFineRequest.getReason());

		Fine savedFine = fineRepo.save(fine);

		return fineMapper.toDTO(savedFine);

	}

	@Override
	public List<FineDTO> getMyFines(FineStatus status, FineType type) throws Exception {

		User currentUser = userService.getCurrentUser();
		List<Fine> fines;

		if (status != null && type != null) {

			fines = fineRepo.findByUserId(currentUser.getId()).stream()
							.filter(f -> f.getStatus() == status && f.getType() == type)
							.collect(Collectors.toList());

		} else if (status != null) {

			fines = fineRepo.findByUserId(currentUser.getId()).stream()
							.filter(f -> f.getStatus() == status)
							.collect(Collectors.toList());

		} else if (type != null) {

			fines = fineRepo.findByUserIdAndType(currentUser.getId(), type);

		} else {

			fines = fineRepo.findByUserId(currentUser.getId());
		}

		return fines
						.stream()
						.map(fineMapper::toDTO)
						.collect(Collectors.toList());
	}

	@Override
	public PageResponse<FineDTO> getAllFines(FineStatus status, FineType type, Long userId, int page, int size) {

		Pageable pageable = PageRequest.of(
						page,
						size,
						Sort.by("createdAt").descending());

		Page<Fine> finePage = fineRepo.findAllWithFilters(
						userId,
						status,
						type,
						pageable);

		return convertToPageResponse(finePage);
	}

	private PageResponse<FineDTO> convertToPageResponse(Page<Fine> finePage) {

		List<FineDTO> fineDTOs = finePage.getContent()
						.stream()
						.map(fineMapper::toDTO)
						.collect(Collectors.toList());

		return new PageResponse<>(
						fineDTOs,
						finePage.getNumber(),
						finePage.getSize(),
						finePage.getTotalElements(),
						finePage.getTotalPages(),
						finePage.isLast(),
						finePage.isFirst(),
						finePage.isEmpty()
		);
	}

}
