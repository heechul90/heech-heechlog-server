package com.heech.heechlog.core.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostDto {

    private Long postId;
    private String postTitle;
    private String postContent;
    private int hits;
    private LocalDateTime createdDate;
    private String createdBy;
}
