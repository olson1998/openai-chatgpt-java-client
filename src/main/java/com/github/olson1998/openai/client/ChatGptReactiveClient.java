package com.github.olson1998.openai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.client.ReactiveRestClient;
import com.github.olson1998.http.contract.ImageDownload;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.openai.model.ex.*;
import com.github.olson1998.openai.model.req.ImageBase64GenerationRequest;
import com.github.olson1998.openai.model.req.ChatRequest;
import com.github.olson1998.openai.model.req.ImageGenerationRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatGptReactiveClient {

    Mono<WebResponse<ChatCompletion>> postChatRequest(ChatRequest chatRequest);

    Mono<WebResponse<ImageGenerations<ImageGeneration>>> postImageGenerationRequest(ImageGenerationRequest imageGenerationRequest);

    Mono<WebResponse<ImageGenerations<ImageURLGeneration>>> postDefaultImageGenerationRequest(ImageGenerationRequest imageGenerationRequest);

    Mono<WebResponse<ImageGenerations<ImageBase64Generation>>> postB64ImageGenerationRequest(ImageBase64GenerationRequest imageBase64GenerationRequest);

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

        Builder reactiveRestClient(ReactiveRestClient reactiveRestClient);

        ChatGptReactiveClient build();
    }
}
