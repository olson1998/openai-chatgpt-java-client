package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.client.ReactiveRestClient;
import com.github.olson1998.http.client.exception.HttpResponseException;
import com.github.olson1998.http.client.util.Base64ImageUtil;
import com.github.olson1998.http.contract.ImageDownload;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.http.serialization.ResponseMapping;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import com.github.olson1998.openaiutil.model.ex.*;
import com.github.olson1998.openaiutil.model.req.ImageBase64GenerationRequest;
import com.github.olson1998.openaiutil.model.req.ChatRequest;
import com.github.olson1998.openaiutil.model.req.CustomImageGenerationRequest;
import com.github.olson1998.openaiutil.model.req.ImageGenerationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.image.BufferedImage;
import java.net.URI;

import static com.github.olson1998.http.HttpMethod.GET;
import static com.github.olson1998.http.HttpMethod.POST;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@RequiredArgsConstructor(access = AccessLevel.MODULE)
class DefaultChatGptReactiveClient implements ChatGptReactiveClient {

    private final URI chatCompletionURI;

    private final URI imageGenerationsURI;

    private final ReactiveRestClient reactiveRestClient;

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
        return reactiveRestClient.sendHttpRequest(httpRequest, ChatCompletion.class)
                .onErrorMap(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException);
    }

    @Override
    public Mono<WebResponse<ImageGenerations<ImageGeneration>>> postImageGenerationRequest(ImageGenerationRequest imageGenerationRequest) {
        ResponseMapping<ImageGenerations<ImageGeneration>> responseMapping = createResponseMapping(imageGenerationRequest);
        var webRequest = WebRequest.builder()
                .uri(imageGenerationsURI)
                .httpMethod(POST)
                .contentType(APPLICATION_JSON)
                .body(imageGenerationRequest)
                .build();
        return reactiveRestClient.sendHttpRequest(webRequest, responseMapping)
                .onErrorMap(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException);
    }

    @Override
    public Mono<WebResponse<ImageGenerations<DefaultImageGeneration>>> postDefaultImageGenerationRequest(ImageGenerationRequest imageGenerationRequest) {
        var request = ImageGenerationRequest.builder()
                .model(imageGenerationRequest.getModel())
                .numberOfImages(imageGenerationRequest.getNumberOfImages())
                .prompt(imageGenerationRequest.getPrompt())
                .size(imageGenerationRequest.getSize())
                .quality(imageGenerationRequest.getQuality())
                .build();
        var webRequest = WebRequest.builder()
                .uri(imageGenerationsURI)
                .httpMethod(POST)
                .contentType(APPLICATION_JSON)
                .body(request)
                .build();
        return reactiveRestClient.sendHttpRequest(webRequest, new ResponseMapping<ImageGenerations<DefaultImageGeneration>>() {
        }).onErrorMap(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException);
    }

    @Override
    public Mono<WebResponse<ImageGenerations<ImageBase64Generation>>> postB64ImageGenerationRequest(ImageBase64GenerationRequest imageBase64GenerationRequest) {
        var webRequest = WebRequest.builder()
                .uri(imageGenerationsURI)
                .httpMethod(POST)
                .contentType(APPLICATION_JSON)
                .body(imageBase64GenerationRequest)
                .build();
        return reactiveRestClient.sendHttpRequest(webRequest, new ResponseMapping<ImageGenerations<ImageBase64Generation>>() {
        }).onErrorMap(HttpResponseException.class, chatGptErrorHandler::doHandleHttpResponseException);
    }

    @Override
    public Flux<ImageDownload> postImageGenerationRequestAndObtain(ImageGenerationRequest imageGenerationRequest) {
        var imageGenerationResponseMono = postImageGenerationRequest(imageGenerationRequest)
                .map(WebResponse::getBody)
                .map(ImageGenerations::getData)
                .onErrorStop();
        return imageGenerationResponseMono.flatMapMany(Flux::fromArray)
                .flatMap(this::obtainImageGeneration);
    }

    private Mono<ImageDownload> obtainImageGeneration(ImageGeneration imageGeneration){
        if(imageGeneration instanceof DefaultImageGeneration defaultImageGeneration){
            return getImageGeneration(defaultImageGeneration);
        } else if (imageGeneration instanceof ImageBase64Generation imageBase64Generation) {
            return readImageFromB64(imageBase64Generation);
        }else {
            throw new IllegalArgumentException("");
        }
    }

    private Mono<ImageDownload> getImageGeneration(DefaultImageGeneration defaultImageGeneration){
        var uri = defaultImageGeneration.getUrl();
        var webRequest = WebRequest.builder()
                .uri(uri)
                .httpMethod(GET)
                .build();
        return reactiveRestClient.sendHttpRequest(webRequest, BufferedImage.class)
                .onErrorContinue(((throwable, o) -> {}))
                .map(WebResponse::getBody)
                .map(image -> new ImageDownload(uri, image));
    }

    private Mono<ImageDownload> readImageFromB64(ImageBase64Generation imageBase64Generation){
        return Mono.just(imageBase64Generation.getB64Image())
                .map(this::deserializeImage);
    }

    private ImageDownload deserializeImage(String b64Image){
        var imageRead = Base64ImageUtil.readBase64Image(b64Image);
        return new ImageDownload(null, null, imageRead);
    }

    private ResponseMapping<ImageGenerations<ImageGeneration>> createResponseMapping(ImageGenerationRequest imageGenerationRequest){
        ResponseMapping<ImageGenerations<ImageGeneration>> imageGenerationsResponseMapping;
        if(imageGenerationRequest instanceof ImageBase64GenerationRequest){
            imageGenerationsResponseMapping = b64Image();
        } else if (imageGenerationRequest instanceof CustomImageGenerationRequest customRequest) {
            var responseFormat = customRequest.getResponseFormat();
            if(responseFormat == null){
                imageGenerationsResponseMapping = defaultImageGeneration();
            } else if(responseFormat.equals(ResponseFormat.B64JSON.getType())){
                imageGenerationsResponseMapping = b64Image();
            }else {
                return null;
            }
        }else {
            imageGenerationsResponseMapping = defaultImageGeneration();
        }
        return imageGenerationsResponseMapping;
    }

    private ResponseMapping<ImageGenerations<ImageGeneration>> defaultImageGeneration(){
        var mapping = new ResponseMapping<ImageGenerations<DefaultImageGeneration>>(){
        };
        return new ResponseMapping<>(mapping.getPojoType()) {
        };
    }

    private ResponseMapping<ImageGenerations<ImageGeneration>> b64Image(){
        var mapping = new ResponseMapping<ImageGenerations<ImageBase64Generation>>(){
        };
        return new ResponseMapping<>(mapping.getPojoType()) {
        };
    }

}
