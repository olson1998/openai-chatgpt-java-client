package com.github.olson1998.openai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.client.ReactiveRestClient;
import com.github.olson1998.http.imageserial.ImageSerializationCodec;
import com.github.olson1998.http.jacksonserial.json.JacksonJsonSerializationCodec;
import com.github.olson1998.http.nettyclient.NettyReactiveRestClient;
import com.github.olson1998.http.serialization.SerializationCodecs;
import lombok.SneakyThrows;
import lombok.ToString;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@ToString
public class ChatGptReactiveClientBuilder implements ChatGptReactiveClient.Builder {
    
    private String baseURI;
    
    private String chatCompletionPath;

    private String imageGenerationPath;
    
    private String authorizationToken;
    
    private ObjectMapper jsonObjectMapper;

    private ReactiveRestClient reactiveRestClient;
    
    @Override
    public ChatGptReactiveClient.Builder openAiBaseURI(String uri) {
        baseURI = uri;
        return this;
    }

    @Override
    public ChatGptReactiveClient.Builder chatCompletionPath(String path) {
        this.chatCompletionPath = path;
        return this;
    }

    @Override
    public ChatGptReactiveClient.Builder imageGenerationsPath(String path) {
        this.imageGenerationPath = path;
        return this;
    }

    @Override
    public ChatGptReactiveClient.Builder authorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
        return this;
    }

    @Override
    public ChatGptReactiveClient.Builder jsonObjectMapper(ObjectMapper objectMapper) {
        this.jsonObjectMapper = objectMapper;
        return this;
    }

    @Override
    public ChatGptReactiveClient.Builder reactiveRestClient(ReactiveRestClient reactiveRestClient) {
        this.reactiveRestClient = reactiveRestClient;
        return this;
    }

    @Override
    @SneakyThrows
    public ChatGptReactiveClient build() {
        Objects.requireNonNull(baseURI);
        Objects.requireNonNull(chatCompletionPath);
        Objects.requireNonNull(jsonObjectMapper);
        var restClient = resolveRestClient();
        restClient.addHttpHeader(AUTHORIZATION, "Bearer " + authorizationToken);
        var chatCompletionUri = new URI(baseURI + chatCompletionPath);
        var imageGenerationsUri = new URI(baseURI + imageGenerationPath);
        return new DefaultChatGptReactiveClient(
                chatCompletionUri,
                imageGenerationsUri,
                restClient,
                new DefaultChatGptErrorHandler(jsonObjectMapper)
        ); 
    }

    private ReactiveRestClient resolveRestClient(){
        return Optional.ofNullable(reactiveRestClient)
                .orElseGet(this::buildDefault);
    }

    private ReactiveRestClient buildDefault(){
        var httpClient = HttpClient.create();
        var codecs = new SerializationCodecs();
        codecs.registerCodec(new JacksonJsonSerializationCodec(jsonObjectMapper));
        codecs.registerCodec(new ImageSerializationCodec());
        return new NettyReactiveRestClient(httpClient, codecs);
    }

}
