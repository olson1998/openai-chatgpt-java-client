package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.ImageDownload;
import com.github.olson1998.http.ReadOnlyHttpHeaders;
import com.github.olson1998.http.client.ReactiveRestClient;
import com.github.olson1998.http.client.exception.HttpResponseException;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.http.serialization.ContentDeserializer;
import com.github.olson1998.http.serialization.ResponseMapping;
import com.github.olson1998.http.serialization.context.ContentSerializationContext;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import com.github.olson1998.openaiutil.model.ex.*;
import com.github.olson1998.openaiutil.model.req.B64ImageUrlGenerationRequest;
import com.github.olson1998.openaiutil.model.req.ChatRequest;
import com.github.olson1998.openaiutil.model.req.CustomImageGenerationRequest;
import com.github.olson1998.openaiutil.model.req.ImageGenerationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.olson1998.http.HttpMethod.GET;
import static com.github.olson1998.http.HttpMethod.POST;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

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
    public <I extends ImageGeneration, G extends ImageGenerations<I>> Mono<WebResponse<G>> postImageGenerationRequest(ImageGenerationRequest imageGenerationRequest) {
        ResponseMapping<G> responseMapping = createResponseMapping(imageGenerationRequest);
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
    public Flux<ImageDownload> postImageGenerationRequestAndObtain(ImageGenerationRequest imageGenerationRequest) {
        var imageGenerationResponseMono = postImageGenerationRequest(imageGenerationRequest)
                .map(WebResponse::body)
                .map(ImageGenerations::getData);
        return imageGenerationResponseMono.flatMapMany(Flux::fromArray)
                .flatMap(this::obtainImageGeneration);
    }

    private Mono<ImageDownload> obtainImageGeneration(ImageGeneration imageGeneration){
        if(imageGeneration instanceof DefaultImageGeneration defaultImageGeneration){
            return getImageGeneration(defaultImageGeneration);
        } else if (imageGeneration instanceof B64EncodedImageUrlGeneration b64ImageGeneration) {
            return readImageFromB64(b64ImageGeneration);
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
                .map(WebResponse::body)
                .map(image -> new ImageDownload(uri, image));
    }

    private Mono<ImageDownload> readImageFromB64(B64EncodedImageUrlGeneration b64EncodedImageUrlGeneration){
        return Mono.just(b64EncodedImageUrlGeneration.getB64Image())
                .map(b64 -> Base64.getDecoder().decode(b64))
                .map(this::deserializeImage);
    }

    private ImageDownload deserializeImage(byte[] imageBytes){
        var codecRef = new AtomicReference<ContentDeserializer>();
        reactiveRestClient.serializationCodecs(serializationCodecs -> codecRef.set(serializationCodecs.getContentDeserializer(IMAGE_PNG)));
        var codec = codecRef.get();
        var image = codec.deserialize(BufferedImage.class).apply(imageBytes, new ContentSerializationContext(IMAGE_PNG, new ReadOnlyHttpHeaders()));
        return new ImageDownload(null, image);
    }

    private <I extends ImageGeneration, G extends ImageGenerations<I>>  ResponseMapping<G> createResponseMapping(ImageGenerationRequest imageGenerationRequest){
        if(imageGenerationRequest instanceof B64ImageUrlGenerationRequest){
            return (ResponseMapping<G>) b64Image();
        } else if (imageGenerationRequest instanceof CustomImageGenerationRequest customRequest) {
            var responseFormat = customRequest.getResponseFormat();
            if(responseFormat.equals(ResponseFormat.B64JSON.getType())){
                return (ResponseMapping<G>) b64Image();
            }else {
                return null;
            }
        }else {
            return (ResponseMapping<G>) defaultImageGeneration();
        }
    }

    private ResponseMapping<ImageGenerations<DefaultImageGeneration>> defaultImageGeneration(){
        return new ResponseMapping<>() {
        };
    }

    private ResponseMapping<ImageGenerations<B64EncodedImageUrlGeneration>> b64Image(){
        return new ResponseMapping<>() {
        };
    }

}
