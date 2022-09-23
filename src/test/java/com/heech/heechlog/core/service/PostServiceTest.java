package com.heech.heechlog.core.service;

import com.heech.heechlog.core.repository.PostQueryRepository;
import com.heech.heechlog.core.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    //CREATE_POST

    //UPDATE_POST

    //ERROR_MESSAGE
    public static final String ENTITY_NAME = "post";

    @InjectMocks PostService postService;

    @Mock PostQueryRepository postQueryRepository;

    @Mock PostRepository postRepository;

    @Test
    void findPosts() {
    }

    @Test
    void findPost() {
    }

    @Test
    void savePost() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }
}