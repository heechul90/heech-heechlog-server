package com.heech.heechlog.core.like.service;

import com.heech.heechlog.core.like.domain.Like;
import com.heech.heechlog.core.like.exception.LikeNotFound;
import com.heech.heechlog.core.like.repository.LikeRepository;
import com.heech.heechlog.core.post.domain.Post;
import com.heech.heechlog.core.post.repository.PostRepository;
import com.heech.heechlog.core.user.domain.User;
import com.heech.heechlog.core.user.exception.UserNotFound;
import com.heech.heechlog.core.user.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRespository userRespository;

    /**
     * 좋아요 추가
     */
    @Transactional
    public Like saveLike(Long userId, Long postId) {
        User findUser = userRespository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Post findPost = postRepository.findById(postId)
                .orElseThrow();

        return likeRepository.save(
                Like.createLike()
                        .user(findUser)
                        .post(findPost)
                        .build()
        );
    }

    /**
     * 좋아요 삭제
     */
    @Transactional
    public void deleteLike(Long likeId) {
        Like findLike = likeRepository.findById(likeId)
                .orElseThrow(LikeNotFound::new);
        likeRepository.delete(findLike);
    }
}
