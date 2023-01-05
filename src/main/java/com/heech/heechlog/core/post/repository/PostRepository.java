package com.heech.heechlog.core.post.repository;

import com.heech.heechlog.core.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
