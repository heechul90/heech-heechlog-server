package com.heech.heechlog.core.controller;

import com.heech.heechlog.common.json.JsonResult;
import com.heech.heechlog.core.controller.request.CreatePostRequest;
import com.heech.heechlog.core.controller.request.UpdatePostRequest;
import com.heech.heechlog.core.controller.response.CreatePostResponse;
import com.heech.heechlog.core.controller.response.UpdatePostResponse;
import com.heech.heechlog.core.domain.Post;
import com.heech.heechlog.core.dto.PostDto;
import com.heech.heechlog.core.dto.PostSearchCondition;
import com.heech.heechlog.core.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * Post 목록 조회
     */
    @GetMapping
    public JsonResult findPosts(PostSearchCondition condition, Pageable pageable) {
        Page<Post> contents = postService.findPosts(condition, pageable);
        List<PostDto> posts = contents.stream()
                .map(post -> PostDto.builder()
                        .postId(post.getId())
                        .postTitle(post.getTitle())
                        .postContent(post.getContent())
                        .hits(post.getHits())
                        .createdDate(post.getCreatedDate())
                        .createdBy(post.getCreatedBy())
                        .build()
                )
                .collect(Collectors.toList());
        return JsonResult.OK(posts);
    }

    /**
     * Post 단건 조회
     */
    @GetMapping("/{postId}")
    public JsonResult findPost(@PathVariable("postId") Long postId) {
        Post findPost = postService.findPost(postId);
        PostDto post = PostDto.builder()
                .postId(findPost.getId())
                .postTitle(findPost.getTitle())
                .postContent(findPost.getContent())
                .hits(findPost.getHits())
                .createdDate(findPost.getCreatedDate())
                .createdBy(findPost.getCreatedBy())
                .build();
        return JsonResult.OK(post);
    }

    /**
     * Post 저장
     */
    @PostMapping
    public JsonResult savePost(@RequestBody CreatePostRequest request) {
        //validate
        request.validate();

        Post savedPost = postService.savePost(request.toPost());

        return JsonResult.OK(new CreatePostResponse(savedPost.getId()));
    }

    /**
     * Post 수정
     */
    @PutMapping("/{postId}")
    public JsonResult updatePost(@PathVariable("postId") Long postId, @RequestBody UpdatePostRequest request) {
        //validate
        request.validate();

        postService.updatePost(postId, request.toParam());
        Post updatedPost = postService.findPost(postId);

        return JsonResult.OK(new UpdatePostResponse(updatedPost.getId(), updatedPost.getTitle()));
    }

    /**
     * Post 삭제
     */
    @DeleteMapping("/{postId}")
    public JsonResult deletePost(@PathVariable("postId") Long postId) {

        postService.deletePost(postId);
        return JsonResult.OK();
    }
}
