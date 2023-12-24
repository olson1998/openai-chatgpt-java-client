package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.openaiutil.model.ex.ChatRequest;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;
import reactor.core.publisher.Mono;

public interface ChatGptReactiveClient {

    Mono<WebResponse<ChatCompletion>> postChatRequest(ChatRequest chatRequest);

    static Builder builder(){
        return new ChatGptReactiveClientBuilder();
    }

    interface Builder{

        Builder openAiBaseURI(String uri);

        Builder chatCompletionPath(String path);

        Builder authorizationToken(String authorizationToken);

        Builder jsonObjectMapper(ObjectMapper objectMapper);

        ChatGptReactiveClient build();
    }
}
