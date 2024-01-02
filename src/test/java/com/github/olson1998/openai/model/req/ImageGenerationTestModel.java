package com.github.olson1998.openai.model.req;

import com.github.olson1998.openai.model.ex.DefaultImageGeneration;
import com.github.olson1998.openai.model.ex.ImageBase64Generation;
import com.github.olson1998.openai.model.ex.ImageGeneration;
import com.github.olson1998.openai.model.ex.ImageGenerations;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;

@UtilityClass
public class ImageGenerationTestModel {

    public static final File BASE64_IMAGE = new File("src/test/resources/_imageb64.txt");

    public static final String REVISED_PROMPT = "Mocked prompt";

    public static final URI IMAGE_URI = URI.create("http://mock.images/s3/png/image-1.png");

    public static final DefaultImageGeneration DEFAULT_IMAGE_GENERATION = new DefaultImageGeneration(REVISED_PROMPT, IMAGE_URI);

    public static final ImageBase64Generation IMAGE_BASE_64_GENERATION = new ImageBase64Generation(REVISED_PROMPT, new String(loadTestImage()));

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
