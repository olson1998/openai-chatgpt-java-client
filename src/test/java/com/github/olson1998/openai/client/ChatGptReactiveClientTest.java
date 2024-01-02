package com.github.olson1998.openai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.client.ReactiveRestClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.olson1998.openai.model.TestConst.*;

@ExtendWith(MockitoExtension.class)
class ChatGptReactiveClientTest {

    @Spy
    protected ObjectMapper objectMapper;

    @Mock
    protected ReactiveRestClient reactiveRestClient;

    protected ChatGptReactiveClient chatGptReactiveClient(){
        return ChatGptReactiveClient.builder()
                .authorizationToken(AUTHORIZATION_TOKEN)
                .openAiBaseURI(OPENAI_BASE_URI)
                .imageGenerationsPath(IMAGES_GENERATION_PATH)
                .chatCompletionPath(CHAT_COMPLETION_PATH)
                .reactiveRestClient(reactiveRestClient)
                .jsonObjectMapper(objectMapper)
                .build();
    }
}
