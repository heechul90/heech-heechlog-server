package com.heech.heechlog.core.service;

import com.heech.heechlog.core.domain.Post;
import com.heech.heechlog.core.dto.PostSearchCondition;
import com.heech.heechlog.core.dto.UpdatePostParam;
import com.heech.heechlog.common.exception.EntityNotFound;
import com.heech.heechlog.core.repository.PostQueryRepository;
import com.heech.heechlog.core.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    public static final String ENTITY_NAME = "post";

    private final PostQueryRepository postQueryRepository;
    private final PostRepository postRepository;

    /**
     * Post 목록 조회
     */
    public Page<Post> findPosts(PostSearchCondition condition, Pageable pageable) {
        return postQueryRepository.findPosts(condition, pageable);
    }

    /**
     * Post 단건 조회
     */
    public Post findPost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFound(ENTITY_NAME, postId));

        //조회수 증가
        findPost.hit();
        return findPost;
    }

    /**
     * Post 저장
     */
    @Transactional
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    /**
     * Post 수정
     */
    @Transactional
    public void updatePost(Long postId, UpdatePostParam param) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFound(ENTITY_NAME, postId));
        findPost.updatePost(param);
    }

    /**
     * Post 삭제
     */
    @Transactional
    public void deletePost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFound(ENTITY_NAME, postId));
        postRepository.delete(findPost);
    }
}
