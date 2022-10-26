package com.heech.heechlog.core.controller.request;

import com.heech.heechlog.common.exception.JsonInvalidRequest;
import com.heech.heechlog.common.json.ErrorCode;
import com.heech.heechlog.core.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreatePostRequest {

    @NotBlank
    private String postTitle;
    private String postContent;

    public Post toPost() {
        return Post.createPostBuilder()
                .title(this.postTitle)
                .content(this.postContent)
                .build();
    }

    public void validate() {
        List<ErrorCode> errorCodes = new ArrayList<>();

        if (errorCodes.size() > 0) {
            throw new JsonInvalidRequest(errorCodes);
        }
    }
}
