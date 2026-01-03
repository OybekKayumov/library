package com.ok.service.impl;

import com.ok.config.JWTProvider;
import com.ok.domain.UserRole;
import com.ok.exception.UserException;
import com.ok.mapper.UserMapper;
import com.ok.model.User;
import com.ok.payload.dto.UserDTO;
import com.ok.payload.response.AuthResponse;
import com.ok.repo.UserRepo;
import com.ok.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JWTProvider jWTProvider;

	@Override
	public AuthResponse login(String username, String password) {
		return null;
	}

	@Override
	public AuthResponse signup(UserDTO req) throws UserException {

		User user = userRepo.findByEmail(req.getEmail());
		if (user == null) {
			throw new UserException("Email already registered!");
		}

		User createdUser  = new User();
		createdUser.setEmail(req.getEmail());
		createdUser.setPassword(passwordEncoder.encode(req.getPassword()));
		createdUser.setPhone(req.getPhone());
		createdUser.setFullName(req.getFullName());
		createdUser.setLastLogin(LocalDateTime.now());
		createdUser.setRole(UserRole.ROLE_USER);

		User savedUser = userRepo.save(createdUser);

		Authentication auth = new UsernamePasswordAuthenticationToken(
						savedUser.getEmail(), savedUser.getPassword());

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = jWTProvider.generateToken(auth);

		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setTitle("Welcome " + createdUser.getFullName());
		response.setMessage("Registered Successfully!");
		response.setUser(UserMapper.toDTO(savedUser));

		return response;
	}

	@Override
	public void createPasswordResetToken(String email) {

	}

	@Override
	public void resetPassword(String token, String newPassword) {

	}
}
