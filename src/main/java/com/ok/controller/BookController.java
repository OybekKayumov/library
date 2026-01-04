package com.ok.controller;

import com.ok.exception.BookException;
import com.ok.payload.dto.BookDTO;
import com.ok.payload.request.BookSearchRequest;
import com.ok.payload.response.ApiResponse;
import com.ok.payload.response.PageResponse;
import com.ok.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

//	@PostMapping("/admin")
//	public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws BookException {
//
//		BookDTO createdBook = bookService.createBook(bookDTO);
//
//		return ResponseEntity.ok(createdBook);
//	}

	@PostMapping("/bulk")
	public ResponseEntity<?> createBooksBulk(
					@Valid @RequestBody List<BookDTO> bookDTOs) throws BookException {

		List<BookDTO> createdBooks = bookService.createBooksBulk(bookDTOs);

		return ResponseEntity.ok(createdBooks);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) throws BookException {

		BookDTO book = bookService.getBookById(id);
		return ResponseEntity.ok(book);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(
					@PathVariable Long id,
					@RequestBody BookDTO bookDTO) throws BookException {

		//try {

			BookDTO updatedBook = bookService.updateBook(id, bookDTO);
			return ResponseEntity.ok(updatedBook);

//		} catch (BookException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteBook(
					@PathVariable Long id) throws BookException {

		bookService.deleteBook(id);

		return ResponseEntity.ok(new ApiResponse("Book deleted successfully",
						true));
	}

	@DeleteMapping("/{id}/permanent")
	public ResponseEntity<ApiResponse> hardDeleteBook(
					@PathVariable Long id) throws BookException {

		bookService.hardDeleteBook(id);

		return ResponseEntity.ok(new ApiResponse("Book permanently deleted ",
						true));
	}

	@GetMapping
	public ResponseEntity<PageResponse<BookDTO>> searchBooks(
					@RequestParam(required = false) Long genreId,
					@RequestParam(required = false, defaultValue = "false") Boolean availableOnly,
					@RequestParam(defaultValue = "true") boolean activeOnly,
					@RequestParam(defaultValue = "0") int page,
					@RequestParam(defaultValue = "20") int size,
					@RequestParam(defaultValue = "createdAt") String sortBy,
					@RequestParam(defaultValue = "DESC") String sortDirection) {

		BookSearchRequest searchRequest = new BookSearchRequest();
		searchRequest.setGenreId(genreId);
		searchRequest.setAvailableOnly(availableOnly);
		searchRequest.setPage(page);
		searchRequest.setSize(size);
		searchRequest.setSortBy(sortBy);
		searchRequest.setSortDirection(sortDirection);

		PageResponse<BookDTO> books = bookService.searchBooksWithFilters(searchRequest);

		return ResponseEntity.ok(books);
	}

	@PostMapping("/search")
	public ResponseEntity<PageResponse<BookDTO>> advancedSearch(
					@RequestBody BookSearchRequest searchRequest) {

		PageResponse<BookDTO> books =
						bookService.searchBooksWithFilters(searchRequest);

		return ResponseEntity.ok(books);
	}

	@GetMapping("/stats")
	public ResponseEntity<BooksStatResponse> getBookStats() {

		long totalActive = bookService.getTotalActiveBooks();
		long totalAvailable = bookService.getTotalAvailableBooks();

		BooksStatResponse stats = new BooksStatResponse(totalActive, totalAvailable);
		return ResponseEntity.ok(stats);
	}

	public static class BooksStatResponse {

		public long totalActiveBooks;
		public long totalAvailableBooks;

		public BooksStatResponse(long totalActiveBooks, long totalAvailableBooks) {
			this.totalActiveBooks = totalActiveBooks;
			this.totalAvailableBooks = totalAvailableBooks;
		}
	}
}
