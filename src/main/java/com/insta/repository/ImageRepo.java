package com.insta.repository;

import com.insta.model.Image;
import com.insta.model.ImageTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
	@Query("SELECT i FROM Image i WHERE i.imageTarget = :target AND i.targetId = :targetId")
	List<Image> findAllByTargetId(@Param("target") ImageTarget target, @Param("targetId") Long targetId);

	@Query("SELECT i FROM Image i WHERE i.imageTarget = :target AND i.targetId = :targetId")
	Image findByTargetId(@Param("target") ImageTarget target, @Param("targetId") Long targetId);

	@Modifying
	@Transactional
	@Query("DELETE FROM Image i WHERE i.imageTarget = :target AND i.targetId = :targetId")
	void deleteAllByTargetId(@Param("target") ImageTarget target, @Param("targetId") Long targetId);
}