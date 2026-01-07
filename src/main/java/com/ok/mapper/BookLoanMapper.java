package com.ok.mapper;

import com.ok.exception.BookException;
import com.ok.model.Book;
import com.ok.model.BookLoan;
import com.ok.model.Genre;
import com.ok.payload.dto.BookDTO;
import com.ok.payload.dto.BookLoanDTO;
import com.ok.repo.GenreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class BookLoanMapper {

	private final GenreRepo genreRepo;

	public BookLoanDTO toDTO(BookLoan bookLoan) {

		if (bookLoan == null) {
			return null;
		}

		BookLoanDTO dto = new BookLoanDTO();
		dto.setId(bookLoan.getId());

		if (bookLoan.getUser() != null) {
			dto.setUserId(bookLoan.getUser().getId());
			dto.setUserName(bookLoan.getUser().getFullName());
			dto.setUserEmail(bookLoan.getUser().getEmail());
		}

		if (bookLoan.getBook() != null) {
			dto.setBookId(bookLoan.getBook().getId());
			dto.setBookTitle(bookLoan.getBook().getTitle());
			dto.setBookIsbn(bookLoan.getBook().getIsbn());
			dto.setBookAuthor(bookLoan.getBook().getAuthor());
			dto.setBookCoverImage(bookLoan.getBook().getCoverImageUrl());
		}

		dto.setType(bookLoan.getType());
		dto.setStatus(bookLoan.getStatus());
		dto.setCheckoutDate(bookLoan.getCheckoutDate());
		dto.setDueDate(bookLoan.getDueDate());
		dto.setRemainingDays(
						ChronoUnit.DAYS.between(
										LocalDate.now(),
										bookLoan.getDueDate()
						)
		);
		dto.setReturnDate(bookLoan.getReturnDate());
		dto.setRenewalCount(bookLoan.getRenewalCount());
		dto.setMaxRenewals(bookLoan.getMaxRenewals());

		dto.setNotes(bookLoan.getNotes());
		dto.setIsOverdue(bookLoan.getIsOverdue());
		dto.setOverdueDays(bookLoan.getOverdueDays());
		dto.setCreatedAt(bookLoan.getCreatedAt());
		dto.setUpdatedAt(bookLoan.getUpdatedAt());

		return dto;

	}

//	public Book toEntity(BookDTO dto) throws BookException {
//		if (dto == null) {
//			return null;
//		}
//
//		Book book = new Book();
//		book.setId(dto.getId());
//		book.setIsbn(dto.getIsbn());
//		book.setTitle(dto.getTitle());
//		book.setAuthor(dto.getAuthor());
//
//		if (dto.getGenreId() != null) {
//			Genre genre = genreRepo.findById(dto.getGenreId())
//							.orElseThrow(() -> new BookException(
//											"Genre with ID " + dto.getGenreId() + " not found"));
//			book.setGenre(genre);
//		}
//
//		book.setPublisher(dto.getPublisher());
//		book.setPublishedDate(dto.getPublicationDate());
//		book.setLanguage(dto.getLanguage());
//		book.setPages(dto.getPages());
//		book.setDescription(dto.getDescription());
//		book.setTotalCopies(dto.getTotalCopies());
//		book.setAvailableCopies(dto.getAvailableCopies());
//		book.setPrice(dto.getPrice());
//		book.setCoverImageUrl(dto.getCoverImageUrl());
//		book.setActive(true);  //* default to active
//
//		return book;
//	}
//
//	public void updateEntityFromDTO(BookDTO dto, Book book) throws BookException {
//		if (dto == null || book == null) {
//			return;
//		}
//
//		book.setTitle(dto.getTitle());
//		book.setAuthor(dto.getAuthor());
//
//		if (dto.getGenreId() != null) {
//			Genre genre = genreRepo.findById(dto.getGenreId())
//							.orElseThrow(() -> new BookException(
//											"Genre with ID " + dto.getGenreId() + " not found"
//							));
//			book.setGenre(genre);
//		}
//
//		book.setPublisher(dto.getPublisher());
//		book.setPublishedDate(dto.getPublicationDate());
//		book.setLanguage(dto.getLanguage());
//		book.setPages(dto.getPages());
//		book.setDescription(dto.getDescription());
//		book.setTotalCopies(dto.getTotalCopies());
//		book.setAvailableCopies(dto.getAvailableCopies());
//		book.setPrice(dto.getPrice());
//		book.setCoverImageUrl(dto.getCoverImageUrl());
//
//		if (dto.getActive() != null) {
//			book.setActive(dto.getActive());
//		}
//	}

}
