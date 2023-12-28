package com.github.olson1998.openaiutil.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.olson1998.http.ImageDownload;
import com.github.olson1998.openaiutil.model.chat.DalleModel;
import com.github.olson1998.openaiutil.model.req.B64ImageUrlGenerationRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Test
    void shouldCreateImageGeneration(){
        var imageRequest = B64ImageUrlGenerationRequest.b64Image()
                .numberOfImages(3)
                .model(DalleModel.DALLE2)
                .size("256x256")
                .prompt("Generate me fishes swimming in swimming pool while gender reveal party")
                .build();
        var indexRef =new AtomicInteger(1);
        var imageGenerations = chatGptReactiveClient().postImageGenerationRequestAndObtain(imageRequest)
                .map(imageDownload -> save(imageDownload, indexRef))
                .collectList()
                .block();
        assertThat(imageGenerations).isNotNull().isNotEmpty();
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
    private File save(ImageDownload imageDownload, AtomicInteger indexRef){
        var image = imageDownload.bufferedImage();
        var testResources = "src/test/resources/%s";
        var fileName = imageDownload.resolveFileName()
                .map(testResources::formatted)
                .orElseGet(()-> testResources.formatted("image-" + indexRef.getAndIncrement() + ".png"));
        var file = new File(fileName);
        ImageIO.write(image, "png", file);
        return file;
    }

}
