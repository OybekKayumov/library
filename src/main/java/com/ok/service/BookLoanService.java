package com.ok.service;

import com.ok.domain.BookLoanStatus;
import com.ok.payload.dto.BookLoanDTO;
import com.ok.payload.request.BookLoanSearchRequest;
import com.ok.payload.request.CheckinRequest;
import com.ok.payload.request.CheckoutRequest;
import com.ok.payload.request.RenewalRequest;
import com.ok.payload.response.PageResponse;

public interface BookLoanService {

	BookLoanDTO checkOutBook(Long userId, CheckoutRequest checkoutRequest) throws Exception;

	BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest);

	BookLoanDTO checkinBookBook(CheckinRequest checkinRequest);

	BookLoanDTO renewCheckout(RenewalRequest renewalRequest);

	PageResponse<BookLoanDTO> getMyBookLoans(
					BookLoanStatus status, int page, int size);

	PageResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request);

	int updateOverdueBookLoan();

}
