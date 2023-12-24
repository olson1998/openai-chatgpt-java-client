package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.client.ReactiveHttpRequestExecutor;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.http.jacksonserial.json.JacksonJsonDeserializer;
import com.github.olson1998.http.jacksonserial.json.JacksonJsonSerializer;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;
import com.github.olson1998.openaiutil.model.ex.ChatRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
class DefaultChatGptReactiveClient implements ChatGptReactiveClient {

    private final URI chatCompletionURI;

    private final ObjectMapper objectMapper;

    private final ReactiveHttpRequestExecutor reactiveHttpRequestExecutor;

    @Override
    public Mono<ChatCompletion> postChatRequest(ChatRequest chatRequest) {
        var httpRequest = WebRequest.builder()
                .uri(chatCompletionURI)
                .httpMethod("POST")
                .addHttpHeader("accept", "application/json")
                .timeoutDuration(Duration.ofSeconds(30))
                .build();
        var jsonSerializer = new JacksonJsonSerializer<ChatRequest>(objectMapper);
        var jsonDeserializer = new JacksonJsonDeserializer<>(objectMapper, ChatCompletion.class);
        return reactiveHttpRequestExecutor.executeHttpRequest(
                httpRequest,
                chatRequest,
                jsonSerializer,
                jsonDeserializer
        ).map(WebResponse::body);
    }

    @Override
    public Mono<WebResponse<byte[]>> executePostChatRequest(ChatRequest chatRequest) {
        var httpRequest = WebRequest.builder()
                .uri(chatCompletionURI)
                .httpMethod("POST")
                .addHttpHeader("accept", "application/json")
                .timeoutDuration(Duration.ofSeconds(30))
                .build();
        return reactiveHttpRequestExecutor.executeHttpRequestForResponseBodyBytes(httpRequest, chatRequest, new JacksonJsonSerializer<>(objectMapper));
    }

}
