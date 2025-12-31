package com.ok.service.impl;

import com.ok.exception.GenreException;
import com.ok.mapper.GenreMapper;
import com.ok.model.Genre;
import com.ok.payload.dto.GenreDTO;
import com.ok.repo.GenreRepo;
import com.ok.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	//!@Autowired
	private final GenreRepo genreRepo;
	private final GenreMapper genreMapper;

	//!public GenreServiceImpl(GenreRepo genreRepo) {this.genreRepo = genreRepo;}

	@Override
	//public Genre createGenre(Genre genre) {
	public GenreDTO createGenre(GenreDTO genreDTO) {

		//return genreRepo.save(genreDTO);

		//todo_ convert to entity format
//		Genre genre = Genre.builder()
//						.code(genreDTO.getCode())
//						.name(genreDTO.getName())
//						.description(genreDTO.getDescription())
//						.displayOrder(genreDTO.getDisplayOrder())
//						.active(true)
//						.build();
//
//		if (genreDTO.getParentGenreId() != null) {
//			Genre parentGenre =
//							genreRepo.findById(genreDTO.getParentGenreId()).get();
//			genre.setParentGenre(parentGenre);
//		}
		Genre genre = genreMapper.toEntity(genreDTO);

		Genre savedGenre = genreRepo.save(genre);

		//todo_ convert to dto format
//		GenreDTO dto = GenreDTO.builder()
//						.id(savedGenre.getId())
//						.code(savedGenre.getCode())
//						.name(savedGenre.getName())
//						.description(savedGenre.getDescription())
//						.displayOrder(savedGenre.getDisplayOrder())
//						.active(savedGenre.getActive())
//						.createdAt(genre.getCreatedAt())
//						.updatedAt(genre.getUpdatedAt())
//						.build();
//
//		if (savedGenre.getParentGenre() != null) {
//
//			dto.setParentGenreId(savedGenre.getParentGenre().getId());
//			dto.setParentGenreName(savedGenre.getParentGenre().getName());
//		}

//		dto.setSubGenre(savedGenre.getSubGenres()
//						.stream()
//						.filter(subGenre -> subGenre.getActive())
//						.map(subGenre -> )
//						);

		//dto.setBookCount((long) (savedGenre.getBook));

		//GenreDTO dto = GenreMapper.toDTO(savedGenre);
		return genreMapper.toDTO(savedGenre);
	}

	@Override
	public List<GenreDTO> getAllGenres() {
		return genreRepo.findAll().stream()
						//.map(genre -> GenreMapper.toDTO(genre))
						.map(genreMapper::toDTO)
						.collect(Collectors.toList());
	}

	@Override
	public GenreDTO getGenreById(Long genreId) throws GenreException {
		Genre genre = genreRepo.findById(genreId).orElseThrow(
						() -> new GenreException("Genre not Found!")
		);

		return genreMapper.toDTO(genre);
	}

	@Override
	public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) throws GenreException {

		Genre existingGenre = genreRepo.findById(genreId).orElseThrow(
						() -> new GenreException("Genre not found!")
		);

		genreMapper.updateEntityFromDTO(genreDTO, existingGenre);
		Genre updatedGenre = genreRepo.save(existingGenre);

		return genreMapper.toDTO(updatedGenre);
	}

	@Override
	public void deleteGenre(Long genreId) {

	}

	@Override
	public void hardDeleteGenre(Long genreId) {

	}

	@Override
	public List<GenreDTO> getAllActiveGenresWithSubGenres() {
		return List.of();
	}

	@Override
	public List<GenreDTO> getTopLevelGenres() {
		return List.of();
	}

	@Override
	public long getTotalActiveGenres() {
		return 0;
	}

	@Override
	public long getBookCountByGenre(Long genreId) {
		return 0;
	}

	//* 1:20 min
}
