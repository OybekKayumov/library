package com.ok.controller;

import com.ok.domain.FineStatus;
import com.ok.domain.FineType;
import com.ok.model.Fine;
import com.ok.payload.dto.FineDTO;
import com.ok.payload.request.CreateFineRequest;
import com.ok.payload.request.WaiveFineRequest;
import com.ok.payload.response.PageResponse;
import com.ok.payload.response.PaymentInitiateResponse;
import com.ok.service.FineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {

	private final FineService finesService;

	@PostMapping
	public ResponseEntity<?> createFine(
					@Valid @RequestBody CreateFineRequest fineRequest) throws Exception {

		FineDTO fineDTO = finesService.createFine(fineRequest);

		return ResponseEntity.ok(fineDTO);
	}

	@PostMapping("/{id}/pay")
	public ResponseEntity<?> payFine(
					@PathVariable Long id,
					@RequestParam(required = false) String transactionId) throws Exception {

		PaymentInitiateResponse res =	finesService.payFine(id, transactionId);

		return ResponseEntity.ok(res);
	}

	@PostMapping
	public ResponseEntity<?> waiveFine(
					@Valid @RequestBody WaiveFineRequest waiveFineRequest) throws Exception {

		FineDTO fineDTO =	finesService.waiveFine(waiveFineRequest);

		return ResponseEntity.ok(fineDTO);
	}

	@GetMapping("/my")
	public ResponseEntity<?> getMayFine(
					@RequestParam(required = false) FineStatus status,
					@RequestParam(required = false) FineType type) throws Exception {

		List<FineDTO> fines =	finesService.getMyFines(status, type);

		return ResponseEntity.ok(fines);
	}

	@GetMapping
	public ResponseEntity<?> getAllFine(
					@RequestParam(required = false) FineStatus status,
					@RequestParam(required = false) FineType type,
					@RequestParam(required = false) Long userId,
					@RequestParam(defaultValue = "0") int page,
					@RequestParam(defaultValue = "20") int size ) throws Exception {

		PageResponse<FineDTO> fines =	finesService.getAllFines(
						status, type, userId, page, size);

		return ResponseEntity.ok(fines);
	}
}
