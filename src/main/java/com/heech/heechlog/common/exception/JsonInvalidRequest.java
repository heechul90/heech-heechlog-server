package com.heech.heechlog.common.exception;

import com.heech.heechlog.common.json.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.List;

public class JsonInvalidRequest extends CommonException {

    public static final String MESSAGE = HttpStatus.BAD_REQUEST.getReasonPhrase();

    public JsonInvalidRequest(List<ErrorCode> errorCodes) {
        super(MESSAGE);
        addErrorCode(errorCodes);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
