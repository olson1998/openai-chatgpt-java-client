package com.github.olson1998.openai.exception;

public class OpenAiResponseException extends RuntimeException {

    public OpenAiResponseException(String message) {
        super(message);
    }

    public OpenAiResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenAiResponseException(Throwable cause) {
        super(cause);
    }
}
