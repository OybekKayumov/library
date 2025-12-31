package com.ok.repo;

import com.ok.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepo extends JpaRepository<Genre,Long> {

	List<Genre> findByActiveTrueOrderByDisplayOrderAsc();

	List<Genre> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

	List<Genre> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(Long parentGenreId);

	long countByActiveTrue();

//	@Query("select count(b) from book b where b.genre.id=:genreId")
//	long countBookByGenre(@Param("genreId")  Long genreId);
}
