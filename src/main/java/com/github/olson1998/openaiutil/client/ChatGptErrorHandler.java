package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.client.exception.HttpResponseException;
import com.github.olson1998.openaiutil.exception.OpenAiErrorResponseException;

public interface ChatGptErrorHandler {

    OpenAiErrorResponseException doHandleHttpResponseException(HttpResponseException httpResponseException);
}
