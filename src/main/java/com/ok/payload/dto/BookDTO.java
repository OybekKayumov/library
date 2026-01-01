package com.ok.payload.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.*;
import lombok.*;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

	private Long id;

	@NotBlank(message = "ISBN is mandatory!")
	private String isbn;

	@NotBlank(message = "Title is mandatory!")
	@Size(min = 1, max = 255,
					message = "Title must be between 1 and 255 characters!")
	private String title;

	@NotBlank(message = "Author is mandatory!")
	@Size(min = 1, max = 255,
					message = "Author must be between 1 and 255 characters!")
	private String author;

	@NotBlank(message = "Genre is mandatory!")
	private Long genreId;

	private String genreName;

	private String genreCode;

	@Size(max = 100, message = "Publisher name must not exceed 100 characters")
	private String publisher;

	private LocalDate publicationDate;

	@Size(max = 20, message = "Language must not exceed 20 characters")
	private String language;

	@Min(value = 1, message = "Pages must be at least 1")
	@Max(value = 50000, message = "Pages must not exceed 50 000")
	private Integer pages;

	@Size(max = 2000, message = "Description must not exceed 2000 characters")
	private String description;

	@Min(value = 0, message = "Total copies cannot be negative")
	@NotNull(message = "Total copies are mandatory")
	private Integer totalCopies;

	@Min(value = 0, message = "Available copies cannot be negative")
	@NotNull(message = "Available copies are mandatory")
	private Integer availableCopies;

	@DecimalMax(value = "0.0", inclusive = true,
					message = "Price cannot be negative")
	@Digits(integer = 8, fraction = 2,
					message = "Price must have at most 8 integer digits and 2 fractions")
	private BigDecimal price;

	@Size(max = 500, message = "Image URL must not exceed 500 characters")
	private String coverImageUrl;

	private Boolean alreadyHaveLoan;
	private Boolean alreadyHaveReservation;

	private Boolean active;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
