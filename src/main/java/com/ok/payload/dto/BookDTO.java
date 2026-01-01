package com.ok.payload.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

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


}
