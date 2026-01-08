package com.ok.service.impl;

import com.ok.domain.FineStatus;
import com.ok.domain.FineType;
import com.ok.payload.dto.FineDTO;
import com.ok.payload.request.CreateFineRequest;
import com.ok.payload.request.WaiveFineRequest;
import com.ok.payload.response.PageResponse;
import com.ok.payload.response.PaymentInitiateResponse;
import com.ok.service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {
	@Override
	public FineDTO createFine(CreateFineRequest createFineRequest) {
		return null;
	}

	@Override
	public PaymentInitiateResponse payFine(Long fineId, String transactionId) {
		return null;
	}

	@Override
	public void markFineAsPaid(Long fineId, Long amount, String transactionId) {

	}

	@Override
	public FineDTO waiveFine(WaiveFineRequest waiveFineRequest) {
		return null;
	}

	@Override
	public List<FineDTO> getMyFines(FineStatus status, FineType type) {
		return List.of();
	}

	@Override
	public PageResponse<FineDTO> getAllFines(FineStatus status, FineType type, Long userId, int page, int size) {
		return null;
	}
}
