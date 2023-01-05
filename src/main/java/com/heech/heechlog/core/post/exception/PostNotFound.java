package com.heech.heechlog.core.post.exception;

import com.heech.heechlog.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class PostNotFound extends CommonException {

    public static final String MESSAGE = "존재하지 않는 Post 입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
