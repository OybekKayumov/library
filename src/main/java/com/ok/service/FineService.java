package com.ok.service;

import com.ok.payload.dto.FineDTO;
import com.ok.payload.request.CreateFineRequest;

public interface FineService {

	FineDTO createFine(CreateFineRequest createFineRequest);
}
