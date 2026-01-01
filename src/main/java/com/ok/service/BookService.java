package com.ok.service;

import com.ok.payload.dto.BookDTO;
import com.ok.payload.request.BookSearchRequest;
import com.ok.payload.response.PageResponse;

import java.util.List;

public interface BookService {

	BookDTO createBook(BookDTO bookDTO);

	List<BookDTO> createBooksBulk();

	BookDTO getBookById(Long bookId);

	BookDTO getBookByISBN(String isbn);

	BookDTO updateBook(Long bookId, BookDTO bookDTO);
	void deleteBook(Long bookId);
	void hardDeleteBook(Long bookId);

	PageResponse<BookDTO> searchBooksWithFilters(
					BookSearchRequest searchRequest
	);

	long getTotalActiveBooks();

	long getTotalAvailableBooks();
}
