package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.openaiutil.model.chat.ChatModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import com.github.olson1998.openaiutil.model.ex.ChatRequest;
import com.github.olson1998.openaiutil.model.ex.Message;
import com.github.olson1998.openaiutil.model.ex.SystemMessage;
import org.junit.jupiter.api.Test;

import java.util.List;

class ChatGptReactiveClientTest {

    private static final String MESSAGE_CONTENT =
            """
            Create me list of 10 addresses in USA, the city name state and zip code must be not fake, street and company can be fake.
            Format list as json list with elements in format:
            {
            "company":"${company_name}"
            "street":"${street_name}"
            "city":${city}",
            "state":"state",
            "zip_code:"${zip_code}"
            }
            """;

    private static final List<Message> MESSAGES = List.of(
            new SystemMessage(MESSAGE_CONTENT)
    );

    private static final ChatRequest CHAT_REQUEST = ChatRequest.builder()
            .responseFormat(ResponseFormat.JSON_OBJECT)
            .model(ChatModel.GPT35TURBO1105)
            .messages(MESSAGES)
            .build();

    @Test
    void shouldWork(){
        var response = chatGptReactiveClient().postChatRequest(CHAT_REQUEST).block();
        System.out.println(response);
    }

    private ChatGptReactiveClient chatGptReactiveClient(){
        return ChatGptReactiveClient.builder()
                .openAiBaseURI("https://api.openai.com")
                .chatCompletionPath("/v1/chat/completions")
                .authorizationToken("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")
                .jsonObjectMapper(new ObjectMapper())
                .build();
    }
}
