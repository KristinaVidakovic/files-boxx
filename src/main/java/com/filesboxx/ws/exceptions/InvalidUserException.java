package com.filesboxx.ws.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidUserException extends AppException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String DEFAULT_MESSAGE = "Forwarded user doesn't exists.";

    HttpStatus status = HTTP_STATUS;

    public InvalidUserException() {
        this(DEFAULT_MESSAGE);
    }

    InvalidUserException(String message) {
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
        return ErrorUtils.getErrorCode(getHttpStatus().value(), "Forwarded user ID doesn't exists.");
    }
}
