package com.ok.controller;

import com.ok.domain.BookLoanStatus;
import com.ok.model.BookLoan;
import com.ok.payload.dto.BookLoanDTO;
import com.ok.payload.request.BookLoanSearchRequest;
import com.ok.payload.request.CheckinRequest;
import com.ok.payload.request.CheckoutRequest;
import com.ok.payload.request.RenewalRequest;
import com.ok.payload.response.ApiResponse;
import com.ok.payload.response.PageResponse;
import com.ok.service.BookLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-loans")
public class BookLoanController {

	private final BookLoanService bookLoanService;

	@PostMapping("/checkout")
	public ResponseEntity<?> checkoutBook(
					@Valid @RequestBody CheckoutRequest checkoutRequest) throws Exception {

		BookLoanDTO bookLoan = bookLoanService.checkoutBook(checkoutRequest);

		return new  ResponseEntity<>(bookLoan, HttpStatus.CREATED);

	}

	@PostMapping("/checkout/user/{userId}")
	public ResponseEntity<?> checkoutBookForUser(
					@PathVariable Long userId,
					@Valid @RequestBody CheckoutRequest checkoutRequest) throws Exception {

		BookLoanDTO bookLoan =
						bookLoanService.checkoutBookForUser(userId, checkoutRequest);

		return new  ResponseEntity<>(bookLoan, HttpStatus.CREATED);

	}

	@PostMapping("/{checkin}")
	public ResponseEntity<?> checkin(
					@Valid @RequestBody CheckinRequest checkinRequest) throws Exception {

		BookLoanDTO bookLoan =
						bookLoanService.checkinBook(checkinRequest);

		return new  ResponseEntity<>(bookLoan, HttpStatus.OK);

	}

	@PostMapping("/renew")
	public ResponseEntity<?> renew(
					@Valid @RequestBody RenewalRequest renewalRequest) throws Exception {

		BookLoanDTO bookLoan =
						bookLoanService.renewCheckout(renewalRequest);

		return new  ResponseEntity<>(bookLoan, HttpStatus.OK);

	}

	@GetMapping("/my")
	public ResponseEntity<?> getMyBookLoans(
					@RequestParam(required = false) BookLoanStatus status,
					@RequestParam(defaultValue = "0") int page,
					@RequestParam(defaultValue = "20") int size) throws Exception {

		PageResponse<BookLoanDTO> bookLoans =
						bookLoanService.getMyBookLoans(status, page, size);

		return ResponseEntity.ok(bookLoans);

	}

	@PostMapping("/search")
	public ResponseEntity<?> getAllBookLoans(
					@RequestBody BookLoanSearchRequest searchRequest) throws Exception {

		PageResponse<BookLoanDTO> bookLoans =
						bookLoanService.getBookLoans(searchRequest);

		return ResponseEntity.ok(bookLoans);

	}

	@PostMapping("/admin/update-overdue")
	public ResponseEntity<?> updateOverdueBookLoans() {

		int updateCount = bookLoanService.updateOverdueBookLoan();

		return ResponseEntity.ok(new ApiResponse(
						"Overdue book loans are updated", true));

	}

}
