package com.ok.service;

import com.ok.domain.FineStatus;
import com.ok.domain.FineType;
import com.ok.payload.dto.FineDTO;
import com.ok.payload.request.CreateFineRequest;
import com.ok.payload.request.WaiveFineRequest;
import com.ok.payload.response.PageResponse;
import com.ok.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface FineService {

	FineDTO createFine(CreateFineRequest createFineRequest) throws Exception;

	PaymentInitiateResponse payFine(Long fineId, String transactionId) throws Exception;

	void markFineAsPaid(Long fineId, Long amount, String transactionId) throws Exception;

	FineDTO waiveFine(WaiveFineRequest waiveFineRequest) throws Exception;

	List<FineDTO> getMyFines(FineStatus status, FineType type) throws Exception;

	PageResponse<FineDTO> getAllFines(
					FineStatus status,
					FineType type,
					Long userId,
					int page,
					int size);

}
