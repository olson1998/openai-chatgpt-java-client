package com.github.olson1998.openai.exception;

import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.openai.model.ex.Error;
import lombok.Getter;

public class OpenAiErrorResponseException extends OpenAiResponseException {

    @Getter
    private final WebResponse<?> errorResponse;

    private OpenAiErrorResponseException(String message, WebResponse<?> errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public static OpenAiErrorResponseException ofDeserializedError(WebResponse<Error>errorWebResponse){
        var msg = "Http request failed, target: 'OpenAi API', status code: %s, deserialized error message:\n- %s".formatted(
                errorWebResponse.getStatusCode(),
                errorWebResponse.getBody()
        );
        return new OpenAiErrorResponseException(msg, errorWebResponse);
    }

    public static OpenAiErrorResponseException of(WebResponse<?>errorWebResponse){
        var msg = "Http request failed, target: 'OpenAi API', status code: %s".formatted(
                errorWebResponse.getHttpHeaders()
        );
        return new OpenAiErrorResponseException(msg, errorWebResponse);
    }
}
