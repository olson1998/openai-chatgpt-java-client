package com.github.olson1998.openaiutil.model.req;

import com.github.olson1998.openaiutil.model.chat.DalleModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import com.github.olson1998.openaiutil.model.ex.DefaultImageGeneration;
import com.github.olson1998.openaiutil.model.ex.ImageBase64Generation;
import com.github.olson1998.openaiutil.model.ex.ImageGeneration;
import com.github.olson1998.openaiutil.model.ex.ImageGenerations;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.stream.Stream;

@UtilityClass
public class ImageGenerationRequestTestModel {

    public static final DalleModel DALLE_MODEL = DalleModel.DALLE3;

    public static final int NUMBER_OF_IMAGES = 1;

    public static final String PROMPT = "Some image";

    public static final String SIZE ="1024x1024";

    public static final File BASE64_IMAGE = new File("src/test/resources/_imageb64.txt");

    public static final String REVISED_PROMPT = "Mocked prompt";

    public static final URI IMAGE_URI = URI.create("http://mock.images/s3/png/image-1.png");

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

    public static final DefaultImageGeneration DEFAULT_IMAGE_GENERATION = new DefaultImageGeneration(REVISED_PROMPT, IMAGE_URI);

    public static final ImageBase64Generation IMAGE_BASE_64_GENERATION =new ImageBase64Generation(REVISED_PROMPT, new String(loadTestImage()));

    public static final ImageGenerations<ImageGeneration> DEFAULT_IMAGE_IMAGE_GENERATIONS =
            createImageGeneration(new DefaultImageGeneration[]{DEFAULT_IMAGE_GENERATION});

    public static final ImageGenerations<ImageGeneration> IMAGE_BASE_64_IMAGE_GENERATIONS =
            createImageGeneration(new ImageBase64Generation[]{IMAGE_BASE_64_GENERATION});

    @SneakyThrows
    public static byte[] loadTestImage(){
        return Files.readAllBytes(BASE64_IMAGE.toPath());
    }

    public static <G extends ImageGeneration> ImageGenerations<ImageGeneration> createImageGeneration(G[] imageGenerationsData){
        return new ImageGenerations<>(System.currentTimeMillis(), imageGenerationsData);
    }

    public static <G extends ImageGeneration> ImageGenerations<G> createTypedImageGeneration(G[] imageGenerationsData){
        return new ImageGenerations<>(System.currentTimeMillis(), imageGenerationsData);
    }

}
