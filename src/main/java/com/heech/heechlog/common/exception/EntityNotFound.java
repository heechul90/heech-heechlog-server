package com.heech.heechlog.common.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends CommonException {

    public EntityNotFound(String entityName, Long entityId) {
        super("존재하지 않는 " + entityName + "입니다. id = " + entityId);
    }

    public EntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
