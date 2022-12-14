package com.heech.heechlog.common.json;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class JsonError {

    private String fieldName;
    private String errorMessage;
}
