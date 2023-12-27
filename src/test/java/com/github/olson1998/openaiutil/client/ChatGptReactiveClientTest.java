package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.ImageDownload;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.contract.WebResponse;
import com.github.olson1998.http.imageserial.ImageSerializationCodec;
import com.github.olson1998.http.jacksonserial.json.JacksonJsonSerializationCodec;
import com.github.olson1998.http.nettyclient.NettyReactiveHttpRequestExecutor;
import com.github.olson1998.http.serialization.SerializationCodecs;
import com.github.olson1998.openaiutil.model.chat.ChatModel;
import com.github.olson1998.openaiutil.model.chat.DalleModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import com.github.olson1998.openaiutil.model.req.ChatRequest;
import com.github.olson1998.openaiutil.model.ex.SystemMessage;
import com.github.olson1998.openaiutil.model.req.ImageGenerationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.spi.IIORegistry;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.olson1998.http.HttpMethod.GET;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.IMAGE_PNG;
import static org.assertj.core.api.Assertions.assertThat;

class ChatGptReactiveClientTest {

    private static final String MESSAGE_CONTENT =
            """
            Create me list of 10 addresses in Poland, the city name state and zip code must be real values, street and company can be fake.
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
    void shouldSaveImages() throws URISyntaxException {
        var imageReq = ImageGenerationRequest.builder()
                .size("256x256")
                .model(DalleModel.DALLE2)
                .numberOfImages(1)
                .prompt("Generate me image of Springer Spaniel running in alpine mountains")
                .build();
        var indexRef = new AtomicInteger(1);
        var fileList = chatGptReactiveClient().postImageGenerationRequestAndObtain(imageReq)
                .map(bufferedImageWebResponse -> saveImage(bufferedImageWebResponse, indexRef))
                .collectList()
                .block();
        assertThat(fileList).isNotNull().isNotEmpty();
        Objects.requireNonNull(fileList).forEach(file -> System.out.println("Created image: " + file.getAbsolutePath()));
    }

    @Test
    void shouldSaveImage() throws URISyntaxException, IOException {
        var uri = new URI("https://oaidalleapiprodscus.blob.core.windows.net/private/org-zpArY0V2YB5k5qpqoaWh3BZ0/user-c19WeNBQ2iiJsqV2DtACyOgm/img-QaLzFgnj3a0FdPW5lLELUsBD.png?st=2023-12-26T09%3A56%3A25Z&se=2023-12-26T11%3A56%3A25Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-12-26T09%3A29%3A18Z&ske=2023-12-27T09%3A29%3A18Z&sks=b&skv=2021-08-06&sig=EvGbHkpWuwCL%2Bj3wsUXM/PQNenCZGc1vIqnga/UmTEc%3D");
        var webReq = WebRequest.builder()
                .httpMethod(GET)
                .uri(uri)
                .addHttpHeader(ACCEPT, IMAGE_PNG.getMimeType())
                .build();
        var client = new NettyReactiveHttpRequestExecutor(HttpClient.create());
        client.serializationCodecs(codecs -> codecs.registerCodec(new ImageSerializationCodec()));
        var download = client.sendHttpRequest(webReq, BufferedImage.class)
                .map(imageWebResponse -> new ImageDownload(uri, imageWebResponse))
                .block();
        assertThat(download).isNotNull();
        var file = new File("src/test/resources/" + download.resolveFileName().orElse("/test-file.png"));
        ImageIO.write(download.response().body(), "png", file);
    }

    private ChatGptReactiveClient chatGptReactiveClient(){
        return ChatGptReactiveClient.builder()
                .openAiBaseURI("https://api.openai.com/v1")
                .chatCompletionPath("/chat/completions")
                .imageGenerationsPath("/images/generations")
                .jsonObjectMapper(new ObjectMapper())
                .build();
    }

    @SneakyThrows
    private File saveImage(ImageDownload imageDownload, AtomicInteger indexRef){
        var response = imageDownload.response();
        var image = response.body();
        var file = new File("src/test/resources/" + imageDownload.resolveFileName().orElse("file-" + indexRef.getAndIncrement()));
        ImageIO.write(image, "png", file);
        return file;
    }

}
