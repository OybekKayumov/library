package com.ok.service.impl;

import com.ok.config.JWTProvider;
import com.ok.domain.UserRole;
import com.ok.exception.UserException;
import com.ok.mapper.UserMapper;
import com.ok.model.PasswordResetToken;
import com.ok.model.User;
import com.ok.payload.dto.UserDTO;
import com.ok.payload.response.AuthResponse;
import com.ok.repo.PasswordResetTokenRepo;
import com.ok.repo.UserRepo;
import com.ok.service.AuthService;
import com.ok.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JWTProvider jWTProvider;
	private final CustomUserServiceImpl customUserServiceImpl;
	private final PasswordResetTokenRepo passwordResetTokenRepo;
	private final EmailService emailService;

	@Override
	public AuthResponse login(String username, String password) throws UserException {

		Authentication authentication = authenticate(username, password);
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		String role = authorities.iterator().next().getAuthority();
		String token = jWTProvider.generateToken(authentication);

		User user = userRepo.findByEmail(username);

		user.setLastLogin(LocalDateTime.now());
		userRepo.save(user);

		AuthResponse response = new AuthResponse();
		response.setTitle("Login successful");
		response.setMessage("Welcome Back " + username);
		response.setJwt(token);
		response.setUser(UserMapper.toDTO(user));

		return response;
	}

	private Authentication authenticate(String username, String password) throws UserException {

		UserDetails userDetails =
						customUserServiceImpl.loadUserByUsername(username);

		if (userDetails == null) {
			throw new UserException("User not found with email " + password);
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new UserException("Wrong password");
		}

		return new UsernamePasswordAuthenticationToken(
						username, null, userDetails.getAuthorities());

	}

	@Override
	public AuthResponse signup(UserDTO req) throws UserException {

		User user = userRepo.findByEmail(req.getEmail());
		if (user != null) {
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

	//@Override
	@Transactional
	public void createPasswordResetToken(String email) throws UserException {

		String frontendUrl = "http://localhost:5173";

		User user = userRepo.findByEmail(email);

		if (user == null) {

			throw new UserException("User not found with given email!");
		}

		String token = UUID.randomUUID().toString();

		PasswordResetToken resetToken = PasswordResetToken.builder()
						.token(token)
						.user(user)
						.expiryDate(LocalDateTime.now().plusMinutes(5))
						.build();

		passwordResetTokenRepo.save(resetToken);
		String resetLink = frontendUrl + token;
		String subject = "Password Reset Request";
		String body = "You requested to reset your password. Use this link (valid" +
						" 5 minutes): " + resetLink;

		//* send email
		emailService.sendEmail(user.getEmail(), subject, body);
	}

	//@Override
	@Transactional
	public void resetPassword(String token, String newPassword) throws Exception {

		PasswordResetToken resetToken =
						passwordResetTokenRepo.findByToken(token)
										.orElseThrow(
														() -> new Exception("Token not valid")
										);

		if (resetToken.isExpired()) {

			passwordResetTokenRepo.delete(resetToken);
			throw new Exception("Token is expired");
		}

		User user = resetToken.getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepo.save(user);
		passwordResetTokenRepo.delete(resetToken);

	}
}
