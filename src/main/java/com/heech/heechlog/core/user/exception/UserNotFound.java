package com.heech.heechlog.core.user.exception;

import com.heech.heechlog.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class UserNotFound extends CommonException {

    public static final String MESSAGE = "존재하지 않는 User 입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
