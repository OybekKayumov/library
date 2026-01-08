package com.ok.repo;

import com.ok.domain.FineStatus;
import com.ok.domain.FineType;
import com.ok.model.Fine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FineRpo extends JpaRepository<Fine, Long> {

	@Query("""
			select f from Fine f 
			where (:userId is null or f.user.id = :userId)
			and (:status is null or f.status = :status)
			and (:type is null or f.type = :type)
			ORDER BY f.createdAt DESC 
	""")
	Page<Fine> findAllWithFilters(
					@Param("userId") Long userId,
					@Param("status")FineStatus status,
					@Param("type")FineType type,
					Pageable pageable);
}
