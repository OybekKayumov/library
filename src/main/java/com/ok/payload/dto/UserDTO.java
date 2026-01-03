package com.ok.payload.dto;

import com.ok.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;

	@NotNull(message = "Email is required!")
	private String email;

	@NotNull(message = "Password is required!")
	private String password;
	private String phone;

	@NotNull(message = "Full Name is required!")
	private String fullName;
	private UserRole role;
	private String username;

	private LocalDateTime lastLogin;
}
