package com.ok.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Service
public class JWTProvider {

	SecretKey key = Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication authentication) {

		Collection<? extends GrantedAuthority> authorities =
						authentication.getAuthorities();

		String roles = populateAuthorities(authorities);

		return Jwts.builder().issuedAt(new Date())
						.expiration(new Date(new Date().getTime() + 86400000))  //* +24H
						.claim("email", authentication.getName())
						.claim("authorities", roles)
						.signWith(key)
						.compact();
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {

		return null;
	}

}
