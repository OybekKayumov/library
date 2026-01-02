package com.ok.service.impl;

import com.ok.exception.BookException;
import com.ok.mapper.BookMapper;
import com.ok.model.Book;
import com.ok.payload.dto.BookDTO;
import com.ok.payload.request.BookSearchRequest;
import com.ok.payload.response.PageResponse;
import com.ok.repo.BookRepo;
import com.ok.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepo bookRepo;
	private final BookMapper bookMapper;

	@Override
	public BookDTO createBook(BookDTO bookDTO) throws BookException {

		if (bookRepo.existsByIsbn(bookDTO.getIsbn())) {
			throw new BookException("Book with ISBN " + bookDTO.getIsbn() +
							 " already exists");
		}

		Book book = bookMapper.toEntity(bookDTO);

		book.isAvailableCopiesValid();
		Book savedBook = bookRepo.save(book);

		return bookMapper.toDTO(savedBook);
	}

	@Override
	public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException {

		List<BookDTO> createdBooks = new ArrayList<>();

		for (BookDTO bookDTO : bookDTOs) {
			BookDTO book = createBook(bookDTO);
			createdBooks.add(book);
		}
		return createdBooks;
	}

	@Override
	public BookDTO getBookById(Long bookId) throws BookException {

		Book book = bookRepo.findById(bookId)
						.orElseThrow(() -> new BookException("Book not found!"));

		return bookMapper.toDTO(book);
	}

	@Override
	public BookDTO getBookByISBN(String isbn) throws BookException {
		Book book = bookRepo.findByIsbn(isbn)
						.orElseThrow(() -> new BookException("Book not found!"));

		return bookMapper.toDTO(book);
	}

	@Override
	public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {

		Book existingBook = bookRepo.findById(bookId)
						.orElseThrow(() -> new BookException("Book not fund!"));

		bookMapper.updateEntityFromDTO(bookDTO, existingBook);
		existingBook.isAvailableCopiesValid();
		Book savedBook = bookRepo.save(existingBook);
		return bookMapper.toDTO(savedBook);
	}

	@Override
	public void deleteBook(Long bookId) throws BookException {

		Book existingBook = bookRepo.findById(bookId).orElseThrow(
						() -> new BookException("Book not found!")
		);

		existingBook.setActive(false);
		bookRepo.save(existingBook);
	}

	@Override
	public void hardDeleteBook(Long bookId) throws BookException {
		Book existingBook = bookRepo.findById(bookId).orElseThrow(
						() -> new BookException("Book not found!")
		);

		bookRepo.delete(existingBook);
	}

	@Override
	public PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {

		Pageable pageable = createPageable(searchRequest.getPage(),
						searchRequest.getSize(),
						searchRequest.getSortBy(),
						searchRequest.getSortDirection());

		Page<Book> bookPage = bookRepo.searchBooksWithFilters(
						searchRequest.getSearchTerm(),
						searchRequest.getGenreId(),
						searchRequest.getAvailableOnly(),
						pageable
		);



		return null;
	}

	@Override
	public long getTotalActiveBooks() {
		return 0;
	}

	@Override
	public long getTotalAvailableBooks() {
		return 0;
	}

	private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {

		size = Math.min(size, 10);
		size = Math.max(size, 1);

		Sort sort = sortDirection.equalsIgnoreCase("ASC")
						?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

		return PageRequest.of(page, size, sort);

	}
}
