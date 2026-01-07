package com.ok.service.impl;

import com.ok.mapper.UserMapper;
import com.ok.model.User;
import com.ok.payload.dto.UserDTO;
import com.ok.repo.UserRepo;
import com.ok.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


	private final UserRepo userRepo;

	@Override
	public User getCurrentUser() throws Exception {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepo.findByEmail(email);

		if (user == null) {
			throw new Exception("User not found");
		}
		return user;
	}

	@Override
	public List<UserDTO> getAllUsers() {

		List<User> users = userRepo.findAll();

		return users.stream()
						.map(UserMapper::toDTO)
						.collect(Collectors.toList());
	}

	@Override
	public User findById(Long id) throws Exception {
		return userRepo.findById(id).orElseThrow(
						() -> new Exception("User not found with given ID!"));
	}
}
