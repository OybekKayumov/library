package com.ok.service;

import com.ok.exception.GenreException;
import com.ok.model.Genre;
import com.ok.payload.dto.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {

	//Genre createGenre(Genre genre);
	GenreDTO createGenre(GenreDTO genre);

	List<GenreDTO> getAllGenres();

	GenreDTO getGenreById(Long genreId) throws GenreException;

	GenreDTO updateGenre(Long genreId, GenreDTO genre) throws GenreException;

	void deleteGenre(Long genreId);

	void hardDeleteGenre(Long genreId);

	List<GenreDTO> getAllActiveGenresWithSubGenres();

	List<GenreDTO> getTopLevelGenres();

	//Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);

	long getTotalActiveGenres();

	long getBookCountByGenre(Long genreId);
}
