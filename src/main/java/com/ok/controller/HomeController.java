package com.ok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping
	public ResponseEntity<String> HomeController() {

		return ResponseEntity.ok("Welcome to Library Management System");
	}
}
