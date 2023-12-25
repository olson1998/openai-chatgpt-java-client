package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.nettyclient.NettyReactiveHttpRequestExecutor;
import com.github.olson1998.openaiutil.model.chat.ChatModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import com.github.olson1998.openaiutil.model.req.ChatRequest;
import com.github.olson1998.openaiutil.model.ex.SystemMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static com.github.olson1998.http.HttpMethod.GET;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.assertj.core.api.Assertions.assertThat;

class ChatGptReactiveClientTest {

    private static final String MESSAGE_CONTENT =
            """
            Create me list of 10 addresses in Poland, the city name state and zip code must be not fake, street and company can be fake.
            Format list as json list with elements in format:
            {
                "${address_number}": {
                    "company":"${company_name}"
                    "street":"${street_name}"
                    "city":${city}",
                    "state":"state",
                    "zip_code:"${zip_code}"
                }
            }
            """;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestAddress{

        private String company;

        private String street;

        private String city;

        private String state;

        @JsonProperty(value = "zip_code")
        private String zipCode;
    }

    @Test
    void shouldWork() throws JsonProcessingException {
        var request = ChatRequest.builder()
                .responseFormat(ResponseFormat.JSON_OBJECT)
                .model(ChatModel.GPT35TURBO1105)
                .messages(List.of(new SystemMessage(MESSAGE_CONTENT)))
                .build();
        var response = chatGptReactiveClient().postChatRequest(request).block();
        assertThat(response).isNotNull();
        var responseBody = response.body();
        assertThat(responseBody).isNotNull();
        var choices = responseBody.getChoices();
        assertThat(choices).isNotNull().hasSizeGreaterThan(0);
        var choice = choices[0];
        assertThat(choice).isNotNull();
        var message = choice.getMessage();
        assertThat(message).isNotNull();
        var messageContent = message.getContent();
        assertThat(messageContent).isNotNull();
        var testAddresses = new ObjectMapper().readValue(messageContent, new TypeReference<Map<String, TestAddress>>() {
        });
        System.out.println(testAddresses.values());
    }

    @Test
    void shouldGetImageBytes() throws URISyntaxException {
        var exec =new NettyReactiveHttpRequestExecutor(HttpClient.create());
        var imageURI = new URI("https://oaidalleapiprodscus.blob.core.windows.net/private/org-zpArY0V2YB5k5qpqoaWh3BZ0/user-c19WeNBQ2iiJsqV2DtACyOgm/img-GZ7kp0BjyJuWzJ7wJevwGvgC.png?st=2023-12-25T13%3A10%3A39Z&se=2023-12-25T15%3A10%3A39Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-12-24T23%3A13%3A16Z&ske=2023-12-25T23%3A13%3A16Z&sks=b&skv=2021-08-06&sig=2qpzS9p1zith96yrYILtrz9Ry52KZytc99YZ8zIoZtE%3D");
        var imageReq = WebRequest.builder()
                .httpMethod(GET)
                .addHttpHeader(ACCEPT, ContentType.IMAGE_PNG.getMimeType())
                .uri(imageURI)
                .build();
        var imageResponse = exec.sendHttpRequest(imageReq).block();
        assertThat(imageResponse).isNotNull();
    }

    private ChatGptReactiveClient chatGptReactiveClient(){
        return ChatGptReactiveClient.builder()
                .openAiBaseURI("https://api.openai.com/v1")
                .chatCompletionPath("/chat/completions")
                .imageGenerationsPath("/images/generations")
                .jsonObjectMapper(new ObjectMapper())
                .build();
    }
}
