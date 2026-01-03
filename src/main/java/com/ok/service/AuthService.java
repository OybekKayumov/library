package com.ok.service;

import com.ok.exception.UserException;
import com.ok.payload.dto.UserDTO;
import com.ok.payload.response.AuthResponse;

public interface AuthService {

	AuthResponse login(String username, String password) throws UserException;
	AuthResponse signup(UserDTO req) throws UserException;

	void createPasswordResetToken(String email) throws UserException;
	void resetPassword(String token, String newPassword);

}
