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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	@PostMapping
	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws BookException {

		BookDTO createdBook = bookService.createBook(bookDTO);

		return ResponseEntity.ok(createdBook);
	}

	@PostMapping("/bulk")
	public ResponseEntity<?> createBooksBulk(
					@Valid @RequestBody List<BookDTO> bookDTOs) throws BookException {

		List<BookDTO> createdBooks = bookService.createBooksBulk(bookDTOs);

		return ResponseEntity.ok(createdBooks);
	}

}
