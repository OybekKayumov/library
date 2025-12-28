package com.ok.service;

import com.ok.model.Genre;
import com.ok.payload.dto.GenreDTO;

import java.util.List;

public interface GenreService {

	//Genre createGenre(Genre genre);
	GenreDTO createGenre(GenreDTO genre);

	List<GenreDTO> getAllGenres();
}
