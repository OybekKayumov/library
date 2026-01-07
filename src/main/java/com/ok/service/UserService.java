package com.ok.service;

import com.ok.model.User;
import com.ok.payload.dto.UserDTO;

import java.util.List;

public interface UserService {

	public User getCurrentUser() throws Exception;

	public List<UserDTO> getAllUsers();

	User findById(Long id) throws Exception;
}
