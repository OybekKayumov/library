package com.ok.controller;

import com.ok.model.User;
import com.ok.payload.dto.UserDTO;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping("/list")
	public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception{
		return ResponseEntity.ok(
						userService.getAllUsers());
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile() throws Exception{
		return ResponseEntity.ok(
						userService.getCurrentUser());
	}

}
