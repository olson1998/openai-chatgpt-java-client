package com.github.olson1998.openai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.client.exception.HttpResponseException;
import com.github.olson1998.http.contract.ClientHttpResponse;
import com.github.olson1998.http.serialization.exception.ContentDeserializationException;
import com.github.olson1998.openai.exception.OpenAiErrorResponseException;
import com.github.olson1998.openai.model.ex.Error;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
class DefaultChatGptErrorHandler implements ChatGptErrorHandler{

    private final ObjectMapper objectMapper;

    @Override
    public OpenAiErrorResponseException doHandleHttpResponseException(HttpResponseException httpResponseException) {
        var sc = httpResponseException.getStatusCode();
        var httpHeaders = httpResponseException.getHttpHeaders();
        var cause = httpResponseException.getCause();
        Object body = null;
        if(cause instanceof ContentDeserializationException contentDeserializationException){
            body = attemptErrorDeserialization(contentDeserializationException);
        }
        if(body instanceof Error error){
            var webResponse = new ClientHttpResponse<>(sc, httpHeaders, error);
            return OpenAiErrorResponseException.ofDeserializedError(webResponse);
        }else {
            var webResponse = new ClientHttpResponse<>(sc, httpHeaders, body);
            return OpenAiErrorResponseException.of(webResponse);
        }
    }

    private Object attemptErrorDeserialization(ContentDeserializationException contentDeserializationException){
        var bytes = contentDeserializationException.getContent();
        var contentType = contentDeserializationException.getContentType();
        if(contentType.getMimeType().equals(APPLICATION_JSON.getMimeType())){
            try{
                return objectMapper.readValue(bytes, Error.class);
            }catch (IOException e){
                return bytes;
            }
        }else {
            return bytes;
        }
    }
}
