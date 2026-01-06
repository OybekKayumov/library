package com.ok.service;

import com.ok.payload.dto.BookLoanDTO;
import com.ok.payload.request.CheckinRequest;
import com.ok.payload.request.CheckoutRequest;

public interface BookLoanService {

	BookLoanDTO checkOutBook(CheckoutRequest checkoutRequest);

	BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest);

	BookLoanDTO checkinBookBook(CheckinRequest checkinRequest);

	BookLoanDTO renewCheckout(RenewalRequest renewalRequest);

}
