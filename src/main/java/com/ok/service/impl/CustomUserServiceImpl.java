package com.ok.service.impl;

import com.ok.model.User;
import com.ok.repo.UserRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

	private final UserRepo userRepo;

	public CustomUserServiceImpl(UserRepo userRepo) {this.userRepo = userRepo;}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());

		Collection<?extends GrantedAuthority> authorities =
						Collections.singleton(authority);

		return new org.springframework.security.core.userdetails.User(
						user.getEmail(),
						user.getPassword(),
						authorities
		);
	}
}
