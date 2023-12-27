package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.ImageDownload;
import com.github.olson1998.http.client.ReactiveHttpRequestExecutor;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;
import com.github.olson1998.openaiutil.model.ex.ImageGenerations;
import com.github.olson1998.openaiutil.model.req.ChatRequest;
import com.github.olson1998.openaiutil.model.req.ImageGenerationRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatGptReactiveClient {

    Mono<WebResponse<ChatCompletion>> postChatRequest(ChatRequest chatRequest);

    Mono<WebResponse<ImageGenerations>> postImageGenerationRequest(ImageGenerationRequest imageGenerationRequest);

    Flux<ImageDownload> postImageGenerationRequestAndObtain(ImageGenerationRequest imageGenerationRequest);

    static Builder builder(){
        return new ChatGptReactiveClientBuilder();
    }

    interface Builder{

        Builder openAiBaseURI(String uri);

        Builder chatCompletionPath(String path);

        Builder imageGenerationsPath(String path);

        Builder authorizationToken(String authorizationToken);

        Builder jsonObjectMapper(ObjectMapper objectMapper);

        Builder reactiveHttpExecutor(ReactiveHttpRequestExecutor reactiveHttpRequestExecutor);

        ChatGptReactiveClient build();
    }
}
