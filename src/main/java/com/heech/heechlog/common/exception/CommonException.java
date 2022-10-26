package com.heech.heechlog.common.exception;

import com.heech.heechlog.common.json.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CommonException extends RuntimeException {

    List<ErrorCode> errorCodes = new ArrayList<>();

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus httpStatus();

    public void addErrorCode(List<ErrorCode> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
