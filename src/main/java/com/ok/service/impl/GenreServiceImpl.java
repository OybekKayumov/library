package com.ok.service.impl;

import com.ok.model.Genre;
import com.ok.payload.dto.GenreDTO;
import com.ok.repo.GenreRepo;
import com.ok.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	//!@Autowired
	private final GenreRepo genreRepo;

	//!public GenreServiceImpl(GenreRepo genreRepo) {this.genreRepo = genreRepo;}

	@Override
	//public Genre createGenre(Genre genre) {
	public GenreDTO createGenre(GenreDTO genreDTO) {

		//return genreRepo.save(genreDTO);

		//todo_ convert to entity format
		Genre genre = Genre.builder()
						.code(genreDTO.getCode())
						.name(genreDTO.getName())
						.description(genreDTO.getDescription())
						.displayOrder(genreDTO.getDisplayOrder())
						.active(true)
						.build();

		if (genreDTO.getParentGenreId() != null) {
			Genre parentGenre =
							genreRepo.findById(genreDTO.getParentGenreId()).get();
			genre.setParentGenre(parentGenre);
		}

		Genre savedGenre = genreRepo.save(genre);

		//todo_ convert to dto format
		GenreDTO dto = GenreDTO.builder()
						.id(savedGenre.getId())
						.code(savedGenre.getCode())
						.name(savedGenre.getName())
						.description(savedGenre.getDescription())
						.displayOrder(savedGenre.getDisplayOrder())
						.active(savedGenre.getActive())
						.createdAt(genre.getCreatedAt())
						.updatedAt(genre.getUpdatedAt())
						.build();

		if (savedGenre.getParentGenre() != null) {

			dto.setParentGenreId(savedGenre.getParentGenre().getId());
			dto.setParentGenreName(savedGenre.getParentGenre().getName());
		}

//		dto.setSubGenre(savedGenre.getSubGenres()
//						.stream()
//						.filter(subGenre -> subGenre.getActive())
//						.map(subGenre -> )
//						);

		//dto.setBookCount((long) (savedGenre.getBook));

		return dto;
	}
}
