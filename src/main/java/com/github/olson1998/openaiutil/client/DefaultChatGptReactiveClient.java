package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.client.ReactiveHttpRequestExecutor;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.http.exception.HttpResponseException;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;
import com.github.olson1998.openaiutil.model.req.ChatRequest;
import com.github.olson1998.openaiutil.model.req.ImageGenerationRequest;
import com.github.olson1998.openaiutil.model.ex.ImageGenerations;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.github.olson1998.http.HttpMethod.POST;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
class DefaultChatGptReactiveClient implements ChatGptReactiveClient {

    private final URI chatCompletionURI;

    private final URI imageGenerationsURI;

    private final ReactiveHttpRequestExecutor reactiveHttpRequestExecutor;

    private final ChatGptErrorHandler chatGptErrorHandler;

    @Override
    public Mono<WebResponse<ChatCompletion>> postChatRequest(ChatRequest chatRequest) {
        var httpRequest = WebRequest.builder()
                .uri(chatCompletionURI)
                .httpMethod(POST)
                .addHttpHeader(ACCEPT, APPLICATION_JSON.getMimeType())
                .contentType(APPLICATION_JSON)
                .body(chatRequest)
                .build();
        return reactiveHttpRequestExecutor.sendHttpRequest(httpRequest, ChatCompletion.class)
                .doOnError(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException)
                .onErrorStop();
    }

    @Override
    public Mono<WebResponse<ImageGenerations>> postImageGenerationRequest(ImageGenerationRequest imageGenerationRequest) {
        var webRequest = WebRequest.builder()
                .uri(imageGenerationsURI)
                .httpMethod(POST)
                .addHttpHeader(ACCEPT, APPLICATION_JSON.getMimeType())
                .contentType(APPLICATION_JSON)
                .body(imageGenerationRequest)
                .build();
        return reactiveHttpRequestExecutor.sendHttpRequest(webRequest, ImageGenerations.class)
                .doOnError(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException)
                .onErrorStop();
    }

}
