package com.github.olson1998.openai.client;

import com.github.olson1998.http.client.exception.HttpResponseException;
import com.github.olson1998.openai.exception.OpenAiErrorResponseException;

public interface ChatGptErrorHandler {

    OpenAiErrorResponseException doHandleHttpResponseException(HttpResponseException httpResponseException);
}
