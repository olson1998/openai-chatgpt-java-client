package com.github.olson1998.openai.exception;

public class OpenAiRequestException extends RuntimeException {

    public OpenAiRequestException(String message) {
        super(message);
    }

    public OpenAiRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenAiRequestException(Throwable cause) {
        super(cause);
    }
}
