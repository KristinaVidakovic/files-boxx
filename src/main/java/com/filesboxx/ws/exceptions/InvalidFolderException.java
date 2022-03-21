package com.filesboxx.ws.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidFolderException extends AppException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String DEFAULT_MESSAGE = "Forwarded folder doesn't exists or is deleted.";

    HttpStatus status = HTTP_STATUS;

    public InvalidFolderException() {
        this(DEFAULT_MESSAGE);
    }

    InvalidFolderException(String message) {
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
        return ErrorUtils.getErrorCode(getHttpStatus().value(), "Forwarded folder ID doesn't exists or is deleted.");
    }
}
