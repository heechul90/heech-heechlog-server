package com.heech.heechlog.core.service;

import com.heech.heechlog.core.domain.Post;
import com.heech.heechlog.core.dto.UpdatePostParam;
import com.heech.heechlog.core.repository.PostQueryRepository;
import com.heech.heechlog.core.repository.PostRepository;
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
    void deletePost() {
        //given

        //when
        postService.deletePost(any(Long.class));

        //then

    }
}