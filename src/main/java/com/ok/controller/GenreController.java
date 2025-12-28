package com.ok.controller;

import com.ok.model.Genre;
import com.ok.payload.dto.GenreDTO;
import com.ok.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

	private final GenreService genreService;

	@PostMapping("/create")
	public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genre) {

		//Genre createdGenre = genreService.createGenre(genre);

		GenreDTO createdGenre = genreService.createGenre(genre);
		return ResponseEntity.ok(createdGenre);
	}

	@GetMapping()
	public ResponseEntity<?> getAllGenres() {

		List<GenreDTO> genres = genreService.getAllGenres();
		return ResponseEntity.ok(genres);
	}
}
