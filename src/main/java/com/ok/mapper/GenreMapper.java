package com.ok.mapper;

import com.ok.model.Genre;
import com.ok.payload.dto.GenreDTO;
import com.ok.repo.GenreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreMapper {

	private final GenreRepo genreRepo;

	//? receive entity and convert it to dto
	public GenreDTO toDTO(Genre savedGenre) {

		if (savedGenre == null) {
			return null;
		}

		GenreDTO dto = GenreDTO.builder()
						.id(savedGenre.getId())
						.code(savedGenre.getCode())
						.name(savedGenre.getName())
						.description(savedGenre.getDescription())
						.displayOrder(savedGenre.getDisplayOrder())
						.active(savedGenre.getActive())
						.createdAt(savedGenre.getCreatedAt())
						.updatedAt(savedGenre.getUpdatedAt())
						.build();

		if (savedGenre.getParentGenre() != null) {

			dto.setParentGenreId(savedGenre.getParentGenre().getId());
			dto.setParentGenreName(savedGenre.getParentGenre().getName());
		}

		if (savedGenre.getSubGenres() != null && !savedGenre.getSubGenres().isEmpty()) {

			dto.setSubGenre(savedGenre.getSubGenres()
							.stream()
							.filter(subGenre -> subGenre.getActive())
							.map(subGenre -> toDTO(subGenre)).collect(Collectors.toList())
			);
		}

		//dto.setBookCount((long) (savedGenre.getBook));

		return dto;
	}

	//?
	public Genre toEntity(GenreDTO genreDTO) {
		if (genreDTO == null) {
			return null;
		}

		Genre genre = Genre.builder()
						.code(genreDTO.getCode())
						.name(genreDTO.getName())
						.description(genreDTO.getDescription())
						.displayOrder(genreDTO.getDisplayOrder())
						.active(true)
						.build();

		if (genreDTO.getParentGenreId() != null) {
			genreRepo.findById(genreDTO.getParentGenreId())
							.ifPresent(genre::setParentGenre);
		}

		return genre;
	}
}
