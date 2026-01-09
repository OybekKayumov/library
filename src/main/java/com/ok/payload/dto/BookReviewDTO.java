package com.ok.payload.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookReviewDTO {

	private Long id;

	@NotNull(message = "User ID is mandatory!")
	private Long userId;

	private String username;

	@NotNull(message = "Book ID is mandatory!")
	private Long bookId;

	private String bookTitle;

	@NotNull(message = "Rating is mandatory!")
	@Min(value = 1)
	@Max(value = 5)
	private Integer rating;

	@NotBlank(message = "Review text is mandatory!")
	@Size(min = 10, max = 2000)
	private String reviewText;

	@Size(max = 200)
	private String title;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;

}
