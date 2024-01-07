package com.github.olson1998.openai.model.req;

import com.github.olson1998.openai.model.chat.DalleModel;
import com.github.olson1998.openai.model.chat.ResponseFormat;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

import static com.github.olson1998.openai.model.chat.DalleModel.DALLE3;

@UtilityClass
public class ImageGenerationRequestTestModel {

    public static final int NUMBER_OF_IMAGES = 1;

    public static final String PROMPT = "Some image";

    public static final String SIZE ="1024x1024";

    public static final ImageGenerationRequest IMAGE_GENERATION_REQUEST = ImageGenerationRequest.builder()
            .model(DALLE3)
            .numberOfImages(NUMBER_OF_IMAGES)
            .prompt(PROMPT)
            .size(SIZE)
            .build();

    public static final CustomImageGenerationRequest CUSTOM_IMAGE_GENERATION_REQUEST = CustomImageGenerationRequest.custom()
            .responseFormat(ResponseFormat.B64JSON)
            .model(DALLE3)
            .numberOfImages(NUMBER_OF_IMAGES)
            .prompt(PROMPT)
            .size(SIZE)
            .build();

    public static final ImageBase64GenerationRequest B_64_IMAGE_URL_GENERATION_REQUEST = ImageBase64GenerationRequest.b64Image()
            .model(DALLE3)
            .numberOfImages(NUMBER_OF_IMAGES)
            .prompt(PROMPT)
            .size(SIZE)
            .build();

    public static Stream<ImageGenerationRequest> testImageGenerations(){
        return Stream.of(IMAGE_GENERATION_REQUEST, CUSTOM_IMAGE_GENERATION_REQUEST, B_64_IMAGE_URL_GENERATION_REQUEST);
    }

}
