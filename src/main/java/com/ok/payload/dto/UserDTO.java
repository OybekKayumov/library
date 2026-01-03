package com.ok.payload.dto;

import com.ok.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;
	private String email;
	private String password;
	private String phone;
	private String fullName;
	private UserRole role;
	private String username;

	private LocalDateTime lastLogin;
}
