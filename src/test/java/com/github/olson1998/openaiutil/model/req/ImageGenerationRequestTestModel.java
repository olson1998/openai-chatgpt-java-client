package com.github.olson1998.openaiutil.model.req;

import com.github.olson1998.openaiutil.model.chat.DalleModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class ImageGenerationRequestTestModel {

    public static final DalleModel DALLE_MODEL = DalleModel.DALLE3;

    public static final int NUMBER_OF_IMAGES = 1;

    public static final String PROMPT = "Some image";

    public static final String SIZE ="1024x1024";

    public static final ImageGenerationRequest IMAGE_GENERATION_REQUEST = ImageGenerationRequest.builder()
            .model(DALLE_MODEL)
            .numberOfImages(NUMBER_OF_IMAGES)
            .prompt(PROMPT)
            .size(SIZE)
            .build();

    public static final CustomImageGenerationRequest CUSTOM_IMAGE_GENERATION_REQUEST = CustomImageGenerationRequest.custom()
            .responseFormat(ResponseFormat.B64JSON)
            .model(DALLE_MODEL)
            .numberOfImages(NUMBER_OF_IMAGES)
            .prompt(PROMPT)
            .size(SIZE)
            .build();

    public static final ImageBase64GenerationRequest B_64_IMAGE_URL_GENERATION_REQUEST = ImageBase64GenerationRequest.b64Image()
            .model(DALLE_MODEL)
            .numberOfImages(NUMBER_OF_IMAGES)
            .prompt(PROMPT)
            .size(SIZE)
            .build();

    public static Stream<ImageGenerationRequest> testImageGenerations(){
        return Stream.of(IMAGE_GENERATION_REQUEST, CUSTOM_IMAGE_GENERATION_REQUEST, B_64_IMAGE_URL_GENERATION_REQUEST);
    }

}
