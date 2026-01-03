package com.ok.mapper;

import com.ok.model.User;
import com.ok.payload.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {

	public static UserDTO toDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFullName(user.getFullName());
		userDTO.setPhone(user.getPhone());
		userDTO.setLastLogin(user.getLastLogin());
		userDTO.setRole(user.getRole());

		return userDTO;
	}

	public static List<UserDTO> toDTOList(List<User> users) {

		return users.stream()
						.map(UserMapper::toDTO)
						.collect(Collectors.toList());
	}

	public static Set<UserDTO> toDTOSet(List<User> users) {

		return users.stream()
						.map(UserMapper::toDTO)
						.collect(Collectors.toSet());
	}

	public static User toEntity(UserDTO userDTO) {

		User createUser = new User();
		createUser.setEmail(userDTO.getEmail());
		createUser.setPassword(userDTO.getPassword());
		createUser.setCreatedAt(LocalDateTime.now());
		createUser.setPhone(userDTO.getPhone());
		createUser.setFullName(userDTO.getFullName());
		createUser.setRole(userDTO.getRole());

		return createUser;
	}


}
