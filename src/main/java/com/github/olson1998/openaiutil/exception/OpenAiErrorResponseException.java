package com.github.olson1998.openaiutil.exception;

import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.openaiutil.model.ex.Error;
import lombok.Getter;

import java.util.Optional;

public class OpenAiErrorResponseException extends OpenAiResponseException {

    @Getter
    private final WebResponse<?> errorResponse;

    private OpenAiErrorResponseException(String message, WebResponse<?> errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public static OpenAiErrorResponseException ofDeserializedError(WebResponse<Error>errorWebResponse){
        var error = Optional.ofNullable(errorWebResponse.body()).map(Error::getError).map(String::valueOf).orElse("?");
        var msg = "Observed Http response error during OpenAi API request execution, error: " + error;
        return new OpenAiErrorResponseException(msg, errorWebResponse);
    }

    public static OpenAiErrorResponseException of(WebResponse<?>errorWebResponse){
        return new OpenAiErrorResponseException("Observed Http response error during OpenAi API request execution", errorWebResponse);
    }
}
