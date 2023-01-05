package com.heech.heechlog.core.like.repository;

import com.heech.heechlog.core.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
