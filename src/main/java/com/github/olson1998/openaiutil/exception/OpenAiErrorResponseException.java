package com.github.olson1998.openaiutil.exception;

import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.openaiutil.model.ex.Error;

public class OpenAiErrorResponseException extends OpenAiResponseException {

    private final WebResponse<?> errorResponse;

    private OpenAiErrorResponseException(String message, WebResponse<?> errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public static OpenAiErrorResponseException ofDeserializedError(WebResponse<Error>errorWebResponse){
        var msg = "Observed Http response error during OpenAi API request execution, error: " + errorWebResponse.body();
        return new OpenAiErrorResponseException(msg, errorWebResponse);
    }

    public static OpenAiErrorResponseException of(WebResponse<?>errorWebResponse){
        return new OpenAiErrorResponseException("Observed Http response error during OpenAi API request execution", errorWebResponse);
    }
}
