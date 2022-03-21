package com.filesboxx.ws.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidArgumentException extends AppException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String DEFAULT_MESSAGE = "Invalid argument!";

    HttpStatus status = HTTP_STATUS;

    public InvalidArgumentException() {
        this(DEFAULT_MESSAGE);
    }

    InvalidArgumentException(String message) {
        super(HTTP_STATUS, message);
    }

    @Override
    @JsonIgnore
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    String getErrorCode() {
        return ErrorUtils.getErrorCode(getHttpStatus().value(), "Invalid argument!");
    }
}
