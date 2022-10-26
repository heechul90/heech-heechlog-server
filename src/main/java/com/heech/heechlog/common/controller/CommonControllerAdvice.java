package com.heech.heechlog.common.controller;

import com.heech.heechlog.common.exception.CommonException;
import com.heech.heechlog.common.json.JsonError;
import com.heech.heechlog.common.json.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonControllerAdvice {

    private final MessageSource messageSource;

    //bindingResult errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<JsonError> errors = e.getFieldErrors().stream()
                .map(error -> JsonError.builder()
                        .fieldName(error.getField())
                        .errorMessage(messageSource.getMessage(error.getCodes()[0], error.getArguments(), request.getLocale()))
                        .build()
                )
                .collect(Collectors.toList());
        return JsonResult.ERROR(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
    }

    //custom errors
    @ExceptionHandler(CommonException.class)
    public JsonResult commonExceptionHandler(CommonException e, HttpServletRequest request) {
        List<JsonError> jsonErrors = e.getErrorCodes().stream()
                .map(code -> JsonError.builder()
                        .fieldName(code.getFieldName())
                        .errorMessage(messageSource.getMessage(code.getErrorCode(), code.getArguments(), request.getLocale()))
                        .build()
                )
                .collect(Collectors.toList());
        return JsonResult.ERROR(e.httpStatus(), e.getMessage(), jsonErrors);
    }
}
