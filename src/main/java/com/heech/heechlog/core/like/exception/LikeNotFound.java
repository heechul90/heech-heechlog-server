package com.heech.heechlog.core.like.exception;

import com.heech.heechlog.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class LikeNotFound extends CommonException {

    public static final String MESSAGE = "존재하지 않는 Like 입니다.";

    public LikeNotFound() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
