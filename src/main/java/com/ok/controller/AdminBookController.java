package com.ok.controller;

import com.ok.exception.BookException;
import com.ok.payload.dto.BookDTO;
import com.ok.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/books")
public class AdminBookController {

	private final BookService bookService;

	@PostMapping
	public ResponseEntity<BookDTO> createBook(
					@Valid @RequestBody BookDTO bookDTO) throws BookException {

		BookDTO createBookDTO = bookService.createBook(bookDTO);
		return ResponseEntity.ok(createBookDTO);

	}
}
