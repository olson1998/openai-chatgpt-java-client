package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.ImageDownload;
import com.github.olson1998.http.client.ReactiveHttpRequestExecutor;
import com.github.olson1998.http.client.exception.HttpResponseException;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;
import com.github.olson1998.openaiutil.model.ex.ImageGeneration;
import com.github.olson1998.openaiutil.model.req.ChatRequest;
import com.github.olson1998.openaiutil.model.req.ImageGenerationRequest;
import com.github.olson1998.openaiutil.model.ex.ImageGenerations;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.olson1998.http.HttpMethod.GET;
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
                .onErrorMap(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException);
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
                .onErrorMap(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException);
    }

    @Override
    public Flux<ImageDownload> postImageGenerationRequestAndObtain(ImageGenerationRequest imageGenerationRequest) {
        return postImageGenerationRequest(imageGenerationRequest)
                .log()
                .map(WebResponse::body)
                .map(this::mapImageURI)
                .flatMapMany(Flux::fromIterable)
                .flatMap(this::getImageGeneration);
    }

    private Mono<ImageDownload> getImageGeneration(URI uri){
        var webReq = WebRequest.builder()
                .httpMethod(GET)
                .uri(uri)
                .build();
        return reactiveHttpRequestExecutor.sendHttpRequest(webReq, BufferedImage.class)
                .log()
                .onErrorContinue((throwable, o) -> System.out.println("Error: " + throwable))
                .map(imageWebResponse -> new ImageDownload(uri, imageWebResponse));
    }

    private Set<URI> mapImageURI(ImageGenerations imageGenerations){
        return Arrays.stream(imageGenerations.getData())
                .map(ImageGeneration::getUrl)
                .collect(Collectors.toSet());
    }

}
