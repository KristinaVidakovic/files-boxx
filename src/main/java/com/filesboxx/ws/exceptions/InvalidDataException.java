package com.filesboxx.ws.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidDataException extends AppException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String DEFAULT_MESSAGE = "Error getting bytes from forwarded file.";

    HttpStatus status = HTTP_STATUS;

    public InvalidDataException() {
        this(DEFAULT_MESSAGE);
    }

    InvalidDataException(String message) {
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
        return ErrorUtils.getErrorCode(getHttpStatus().value(), "Error getting bytes from forwarded file.");
    }
}
