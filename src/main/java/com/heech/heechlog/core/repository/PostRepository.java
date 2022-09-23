package com.heech.heechlog.core.repository;

import com.heech.heechlog.core.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
