package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.nettyclient.NettyReactiveHttpRequestExecutor;
import io.netty.handler.logging.LogLevel;
import lombok.SneakyThrows;
import lombok.ToString;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@ToString
public class ChatGptReactiveClientBuilder implements ChatGptReactiveClient.Builder {
    
    private String baseURI;
    
    private String chatCompletionPath;
    
    private String authorizationToken;
    
    private ObjectMapper jsonObjectMapper;
    
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
    @SneakyThrows
    public ChatGptReactiveClient build() {
        Objects.requireNonNull(baseURI);
        Objects.requireNonNull(chatCompletionPath);
        Objects.requireNonNull(jsonObjectMapper);
        var httpClient = HttpClient.create()
                .doOnRequest((httpClientRequest, connection) -> httpClientRequest.addHeader("Authorization", "Bearer "+ authorizationToken));
        var restExec = new NettyReactiveHttpRequestExecutor(httpClient);
        var chatCompletionUri = new URI(baseURI + chatCompletionPath);
        return new DefaultChatGptReactiveClient(
                chatCompletionUri,
                jsonObjectMapper,
                restExec
        ); 
    }

}
