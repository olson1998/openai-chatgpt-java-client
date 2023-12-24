package com.github.olson1998.openaiutil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.openaiutil.client.ChatGptReactiveClient;
import com.github.olson1998.openaiutil.model.chat.ChatModel;
import com.github.olson1998.openaiutil.model.ex.ChatRequest;
import com.github.olson1998.openaiutil.model.ex.Message;
import com.github.olson1998.openaiutil.model.ex.UserMessage;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class ChatGptReactiveClientTest {

    @Test
    void shouldTest(){
        var messages = new LinkedList<Message>();
        messages.add(new UserMessage("Give me example of successful response for chat completion api"));
        var chatReq = ChatRequest.builder()
                .model(ChatModel.GPT40613)
                .messages(messages)
                .build();
        var response = chatGptReactiveClient().postChatRequest(chatReq).block();
        System.out.println(response);
    }

    private ChatGptReactiveClient chatGptReactiveClient(){
        return ChatGptReactiveClient.builder()
                .openAiBaseURI("https://api.openai.com")
                .chatCompletionPath("/v1/chat/completions")
                .jsonObjectMapper(new ObjectMapper())
                .build();
    }
}
