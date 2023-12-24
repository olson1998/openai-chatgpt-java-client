package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.client.ReactiveHttpRequestExecutor;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.http.exception.HttpRequestException;
import com.github.olson1998.http.exception.HttpResponseException;
import com.github.olson1998.http.serialization.ResponseMapping;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;
import com.github.olson1998.openaiutil.model.ex.ChatRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
class DefaultChatGptReactiveClient implements ChatGptReactiveClient {

    private final URI chatCompletionURI;

    private final ReactiveHttpRequestExecutor reactiveHttpRequestExecutor;

    private final ChatGptErrorHandler chatGptErrorHandler;

    @Override
    public Mono<WebResponse<ChatCompletion>> postChatRequest(ChatRequest chatRequest) {
        var httpRequest = WebRequest.builder()
                .uri(chatCompletionURI)
                .httpMethod("POST")
                .addHttpHeader(ACCEPT, APPLICATION_JSON.getMimeType())
                .timeoutDuration(Duration.ofSeconds(30))
                .contentType(APPLICATION_JSON)
                .body(chatRequest)
                .build();
        var responseMapping = new ResponseMapping<ChatCompletion>(){
        };
        return reactiveHttpRequestExecutor.sendHttpRequest(httpRequest, responseMapping)
                .doOnError(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException)
                .onErrorStop();
    }

}
