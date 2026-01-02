package com.ok.config;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {

		return http
						.sessionManagement(management -> management.sessionCreationPolicy(
										SessionCreationPolicy.STATELESS
						))
						.authorizeHttpRequests(Authorize -> Authorize
										.requestMatchers("/api/**").authenticated()
										.requestMatchers("/api/admin/**").hasRole("ADMIN")
										.anyRequest().permitAll()
						)
						.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
						.csrf(AbstractHttpConfigurer::disable)
						.cors(cors -> cors.configurationSource(corsConfigurationSource()))
						.build();
	}

	private CorsConfigurationSource corsConfigurationSource() {

		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowCredentials(true);
				cfg.setAllowedOrigins(
								Arrays.asList(
												"http://localhost:5173",
												"https://mylibrary-test.com"
								)
				);

				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Collections.singletonList("Authorization"));
				cfg.setMaxAge(360L);
				return cfg;
			}
		};
	}
}
