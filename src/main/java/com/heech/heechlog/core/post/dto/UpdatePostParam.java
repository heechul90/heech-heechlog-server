package com.heech.heechlog.core.post.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdatePostParam {

    private String title;
    private String content;

}
