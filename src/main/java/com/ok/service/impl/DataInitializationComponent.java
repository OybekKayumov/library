package com.ok.service.impl;

import com.ok.domain.UserRole;
import com.ok.model.User;
import com.ok.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		initializeAdminUser();
	}

	private void initializeAdminUser() {

		String adminEmail = "testtest@gmail.com";
		String adminPassword = "testtest";

		if (userRepo.findByEmail(adminEmail) == null) {

			User user = User.builder()
							.password(passwordEncoder.encode(adminPassword))
							.email(adminEmail)
							.fullName("Code with Me")
							.role(UserRole.ROLE_ADMIN)
							.build();

			 User admin = userRepo.save(user);
		}
	}

}
