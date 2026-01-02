package com.ok.service;

import com.ok.exception.BookException;
import com.ok.payload.dto.BookDTO;
import com.ok.payload.request.BookSearchRequest;
import com.ok.payload.response.PageResponse;

import java.util.List;

public interface BookService {

	BookDTO createBook(BookDTO bookDTO) throws BookException;

	List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException;

	BookDTO getBookById(Long bookId) throws BookException;

	BookDTO getBookByISBN(String isbn) throws BookException;

	BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException;
	void deleteBook(Long bookId) throws BookException;
	void hardDeleteBook(Long bookId) throws BookException;

	PageResponse<BookDTO> searchBooksWithFilters(
					BookSearchRequest searchRequest
	);

	long getTotalActiveBooks();

	long getTotalAvailableBooks();
}
