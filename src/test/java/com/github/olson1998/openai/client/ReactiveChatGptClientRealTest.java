package com.github.olson1998.openai.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.openai.model.chat.ChatModel;
import com.github.olson1998.openai.model.chat.DalleModel;
import com.github.olson1998.openai.model.req.ChatRequest;
import com.github.olson1998.openai.model.req.ImageBase64GenerationRequest;
import com.github.olson1998.openai.model.req.SystemMessage;
import com.github.olson1998.openai.model.req.UserMessage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveChatGptClientRealTest {

    @Test
    void should(){
        var chatgptClient = chatGptReactiveClient();
        var messages = List.of(
                new SystemMessage("Generate prompt text based on user message"),
                new UserMessage("Create image of dragons flying around Boston, MA firing all historic buildings")
        );
        var chatReq = ChatRequest.builder()
                .model(ChatModel.GPT40613)
                .messages(messages)
                .temperature(0.1)
                .maxTokens(60)
                .build();
        var response = chatgptClient.postChatRequest(chatReq)
                .block();
        assertThat(response).isNotNull();
        var chatCompletion = response.getBody();
        assertThat(chatCompletion).isNotNull();
        System.out.println(chatCompletion.getChoices());
        var choices = chatCompletion.getChoices();
        assertThat(choices).isNotNull().hasSize(1);
        var choice = choices.get(0);
        assertThat(choice).isNotNull();
        var msg = choice.getMessage();
        assertThat(msg).isNotNull();
        var content = msg.getContent();
        assertThat(content).isNotNull();
        var imageGenReq = ImageBase64GenerationRequest.b64Image()
                .numberOfImages(3)
                .model(DalleModel.DALLE2)
                .prompt(content)
                .size("256x256")
                .build();
    }

    private ChatGptReactiveClient chatGptReactiveClient(){
        return ChatGptReactiveClient.builder()
                .openAiBaseURI("https://api.openai.com/v1")
                .chatCompletionPath("/chat/completions")
                .imageGenerationsPath("/images/generations")
                .authorizationToken("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")
                .jsonObjectMapper(new ObjectMapper())
                .build();
    }

}
