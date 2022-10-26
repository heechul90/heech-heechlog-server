package com.heech.heechlog.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heech.heechlog.common.exception.EntityNotFound;
import com.heech.heechlog.common.json.JsonResult;
import com.heech.heechlog.core.controller.request.CreatePostRequest;
import com.heech.heechlog.core.controller.request.UpdatePostRequest;
import com.heech.heechlog.core.domain.Post;
import com.heech.heechlog.core.dto.PostSearchCondition;
import com.heech.heechlog.core.dto.SearchCondition;
import com.heech.heechlog.core.dto.UpdatePostParam;
import com.heech.heechlog.core.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(PostController.class)
//@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    //REQUEST_URL
    public static final String API_FIND_POSTS = "/api/posts";
    public static final String API_FIND_POST = "/api/posts/{postId}";
    public static final String API_SAVE_POST = "/api/posts";
    public static final String API_UPDATE_POST = "/api/posts/{postId}";
    public static final String API_DELETE_POST = "/api/posts/{postId}";

    //CREATE_POST
    public static final String POST_TITLE = "post_title";
    public static final String POST_CONTENT = "post_content";

    //UPDATE_POST
    public static final String UPDATE_TITLE = "update_title";
    public static final String UPDATE_CONTENT = "update_content";

    //ERROR_MESSAGE
    public static final String ENTITY_NAME = "post";
    public static final Long NOT_FOUND_ID = 1L;
    public static final String ENTITY_NOT_FOUND_MESSAGE = "존재하지 않는 " + ENTITY_NAME + "입니다. id = " + NOT_FOUND_ID;

    @Autowired private MockMvc mockMvc;

    @MockBean private PostService postService;

    @Autowired ObjectMapper objectMapper;

    private Post getPost(String postTitle, String postContent) {
        return Post.createPostBuilder()
                .title(postTitle)
                .content(postContent)
                .build();
    }

    @Test
    @DisplayName("post 목록 조회 API")
    void findPosts() throws Exception {
        //given
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            posts.add(getPost(POST_TITLE + i, POST_CONTENT));
        }
        given(postService.findPosts(any(PostSearchCondition.class), any(Pageable.class))).willReturn(new PageImpl<>(posts));

        //검색 안먹힘
        //검색 조건
        PostSearchCondition condition = new PostSearchCondition();
        condition.setSearchCondition(SearchCondition.TITLE);
        condition.setSearchKeyword("0");

        LinkedMultiValueMap<String, String> conditionParams = new LinkedMultiValueMap<>();
        conditionParams.setAll(objectMapper.convertValue(condition, new TypeReference<Map<String, String>>() {}));

        //페이지 조건
        PageRequest pageRequest = PageRequest.of(0, 10);
        LinkedMultiValueMap<String, String> pageRequestParams = new LinkedMultiValueMap<>();
        pageRequestParams.add("page", String.valueOf(pageRequest.getOffset()));
        pageRequestParams.add("size", String.valueOf(pageRequest.getPageSize()));

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_POSTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParams(conditionParams)
                        .queryParams(pageRequestParams)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(30)))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).findPosts(any(PostSearchCondition.class), any(Pageable.class));
    }

    @Test
    @DisplayName("post 단건 조회 API")
    void findPost() throws Exception {
        //given
        Post post = getPost(POST_TITLE, POST_CONTENT);
        given(postService.findPost(any(Long.class))).willReturn(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_POST, 0L)
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.postTitle").value(POST_TITLE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.postContent").value(POST_CONTENT))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).findPost(any(Long.class));
    }

    @Test
    @DisplayName("post 단건 조회 API_ENTITY NOT FOUND")
    void findPost_exception() throws Exception {
        //given
        given(postService.findPost(any(Long.class))).willThrow(new EntityNotFound(ENTITY_NAME, NOT_FOUND_ID));

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_POST, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ENTITY_NOT_FOUND_MESSAGE))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).findPost(any(Long.class));
    }

    @Test
    @DisplayName("post 저장 API")
    void savePost() throws Exception {
        //given
        Post post = getPost(POST_TITLE, POST_CONTENT);
        given(postService.savePost(any(Post.class))).willReturn(post);

        CreatePostRequest request = new CreatePostRequest(POST_TITLE, POST_CONTENT);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post(API_SAVE_POST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).savePost(any(Post.class));
    }

    @Test
    @DisplayName("post 수정 API")
    void updatePost() throws Exception {
        //given
        Post post = getPost(UPDATE_TITLE, UPDATE_CONTENT);
        given(postService.findPost(any(Long.class))).willReturn(post);

        UpdatePostRequest request = new UpdatePostRequest(UPDATE_TITLE, UPDATE_CONTENT);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.put(API_UPDATE_POST, 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).updatePost(any(Long.class), any(UpdatePostParam.class));
    }

    @Test
    @DisplayName("post 수정 API_ENTITY NOT FOUND")
    void updatePost_exception() throws Exception {
        //given
        given(postService.findPost(any(Long.class))).willThrow(new EntityNotFound(ENTITY_NAME, NOT_FOUND_ID));

        UpdatePostRequest request = new UpdatePostRequest(UPDATE_TITLE, UPDATE_CONTENT);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.put(API_UPDATE_POST, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ENTITY_NOT_FOUND_MESSAGE))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).findPost(any(Long.class));
    }

    @Test
    @DisplayName("post 삭제 API")
    void deletePost() throws Exception {
        //given
        Post post = getPost(POST_TITLE, POST_CONTENT);
        given(postService.findPost(any(Long.class))).willReturn(post);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.delete(API_DELETE_POST, 0L)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.name()))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).deletePost(any(Long.class));
    }

    @Test
    @DisplayName("post 삭제 API_ENTITY NOT FOUND")
    void deletePost_exception() throws Exception {
        //given
        given(postService.findPost(any(Long.class))).willThrow(new EntityNotFound(ENTITY_NAME, NOT_FOUND_ID));

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get(API_DELETE_POST, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ENTITY_NOT_FOUND_MESSAGE))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(postService, times(1)).findPost(any(Long.class));
    }
}