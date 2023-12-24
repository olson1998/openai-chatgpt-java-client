package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.exception.HttpResponseException;

public interface ChatGptErrorHandler {

    void doHandleHttpResponseException(HttpResponseException httpResponseException);
}
