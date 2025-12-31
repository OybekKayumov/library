package com.ok.controller;

import com.ok.exception.GenreException;
import com.ok.model.Genre;
import com.ok.payload.dto.GenreDTO;
import com.ok.payload.response.ApiResponse;
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

	@GetMapping("{genreId")
	public ResponseEntity<?> getGenreById(@RequestParam("genreId") Long genreId) throws GenreException {

		GenreDTO genres = genreService.getGenreById(genreId);
		return ResponseEntity.ok(genres);
	}

	@PutMapping("{genreId")
	public ResponseEntity<?> updateGenre(@RequestParam("genreId") Long genreId,
	                                     @RequestBody GenreDTO genre
	                                     ) throws GenreException {

		GenreDTO genres = genreService.updateGenre(genreId, genre);
		return ResponseEntity.ok(genres);
	}

	@DeleteMapping("{genreId")
	public ResponseEntity<?> deleteGenre(@RequestParam("genreId") Long genreId) throws GenreException {

		genreService.deleteGenre(genreId);
		ApiResponse response = new ApiResponse("Genre deleted - soft delete", true);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("{genreId/hard")
	public ResponseEntity<?> hardDeleteGenre(@RequestParam("genreId") Long genreId) throws GenreException {

		genreService.hardDeleteGenre(genreId);
		ApiResponse response = new ApiResponse("Genre deleted - hard delete",
						true);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/top-level")
	public ResponseEntity<?> getTopLevelGenres() {

		List<GenreDTO> genres = genreService.getTopLevelGenres();

		return ResponseEntity.ok(genres);
	}

	@GetMapping("/count")
	public ResponseEntity<?> getTotalActiveGenres() {

		Long genres = genreService.getTotalActiveGenres();

		return ResponseEntity.ok(genres);
	}

	@GetMapping("/{id}/book-count")
	public ResponseEntity<?> getBookCountByGenres(
					@PathVariable Long id
	) {

		Long count = genreService.getBookCountByGenre(id);

		return ResponseEntity.ok(count);
	}

}
