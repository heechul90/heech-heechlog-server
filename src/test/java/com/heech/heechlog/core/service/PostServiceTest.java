package com.heech.heechlog.core.service;

import com.heech.heechlog.common.exception.EntityNotFound;
import com.heech.heechlog.core.domain.Post;
import com.heech.heechlog.core.dto.UpdatePostParam;
import com.heech.heechlog.core.repository.PostQueryRepository;
import com.heech.heechlog.core.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    //CREATE_POST
    public static final String POST_TITLE = "post_title";
    public static final String POST_CONTENT = "post_content";

    //UPDATE_POST
    public static final String UPDATE_TITLE = "update_title";
    public static final String UPDATE_CONTENT = "update_content";

    //ERROR_MESSAGE
    public static final String ENTITY_NAME = "post";
    public static final Long NOT_FOUND_ID = 1L;
    public static final String HAS_MESSAGE_STARTING_WITH = "존재하지 않는 ";
    public static final String HAS_MESSAGE_ENDING_WITH = "id = ";


    @InjectMocks PostService postService;

    @Mock PostQueryRepository postQueryRepository;

    @Mock PostRepository postRepository;

    private Post getPost(String title, String content) {
        return Post.createPostBuilder()
                .title(title)
                .content(content)
                .build();
    }

    @Test
    void findPosts() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("post 단건 조회")
    void findPost() {
        //given
        Post post = getPost(POST_TITLE, POST_CONTENT);
        given(postRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(post));

        //when
        Post findPost = postService.findPost(any(Long.class));

        //then
        assertThat(findPost.getTitle()).isEqualTo(POST_TITLE);
        assertThat(findPost.getContent()).isEqualTo(POST_CONTENT);
        assertThat(findPost.getHits()).isEqualTo(1);

        //verify
        verify(postRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("post 저장")
    void savePost() {
        //given
        Post post = getPost(POST_TITLE, POST_CONTENT);
        given(postRepository.save(any(Post.class))).willReturn(post);

        //when
        Post savedPost = postService.savePost(post);

        //then
        assertThat(savedPost.getTitle()).isEqualTo(POST_TITLE);
        assertThat(savedPost.getContent()).isEqualTo(POST_CONTENT);
        assertThat(savedPost.getHits()).isEqualTo(0);

        //verify
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("post 수정")
    void updatePost() {
        //given
        Post post = getPost(POST_TITLE, POST_CONTENT);
        given(postRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(post));

        UpdatePostParam param = UpdatePostParam.builder()
                .title(UPDATE_TITLE)
                .content(UPDATE_CONTENT)
                .build();

        //when
        postService.updatePost(any(Long.class), param);

        //then
        assertThat(post.getTitle()).isEqualTo(UPDATE_TITLE);
        assertThat(post.getContent()).isEqualTo(UPDATE_CONTENT);
        assertThat(post.getHits()).isEqualTo(0);

        //verify
        verify(postRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("post 삭제")
    void deletePost() {
        //given
        Post post = getPost(POST_TITLE, POST_CONTENT);
        given(postRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(post));


        //when
        postService.deletePost(any(Long.class));

        //verify
        verify(postRepository, times(1)).delete(any(Post.class));
    }

    @Test
    @DisplayName("post 단건 조회_예외")
    void findPost_exception() {
        //given
        given(postRepository.findById(any(Long.class))).willThrow(new EntityNotFound(ENTITY_NAME, NOT_FOUND_ID));

        //expected
        assertThatThrownBy(() -> postService.findPost(NOT_FOUND_ID))
                .isInstanceOf(EntityNotFound.class)
                .hasMessageStartingWith(HAS_MESSAGE_STARTING_WITH + ENTITY_NAME)
                .hasMessageEndingWith(HAS_MESSAGE_ENDING_WITH + NOT_FOUND_ID);

        //verify
        verify(postRepository, times(1)).findById(NOT_FOUND_ID);
    }
}