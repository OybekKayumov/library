package com.ok.payload.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistDTO {

	private Long id;
	private Long userId;
	private String userFullName;
	private BookDTO book;
	private LocalDateTime addedAt;
	private String notes;

}
