package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.client.util.Base64ImageUtil;
import com.github.olson1998.http.contract.ImageDownload;
import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.http.serialization.ResponseMapping;
import com.github.olson1998.openaiutil.model.TestUtil;
import com.github.olson1998.openaiutil.model.ex.DefaultImageGeneration;
import com.github.olson1998.openaiutil.model.ex.ImageBase64Generation;
import com.github.olson1998.openaiutil.model.ex.ImageGeneration;
import com.github.olson1998.openaiutil.model.ex.ImageGenerations;
import com.github.olson1998.openaiutil.model.req.CustomImageGenerationRequest;
import com.github.olson1998.openaiutil.model.req.ImageBase64GenerationRequest;
import com.github.olson1998.openaiutil.model.req.ImageGenerationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.olson1998.http.HttpMethod.GET;
import static com.github.olson1998.http.HttpMethod.POST;
import static com.github.olson1998.http.util.ImageExtension.PNG;
import static com.github.olson1998.openaiutil.model.req.ImageGenerationRequestTestModel.*;
import static com.github.olson1998.openaiutil.model.req.ImageGenerationTestModel.*;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class ReactiveChatGptClientImageGenerationsTest extends ChatGptReactiveClientTest{

    private static final TestUtil.TypeMapping IMAGE_GENERATIONS_DEFAULT_IMAGE_MAPPING = new TestUtil.TypeMapping(
            ImageGenerations.class,
            new TestUtil.TypeMapping[]{new TestUtil.TypeMapping(DefaultImageGeneration.class, null)}
    );

    private static final TestUtil.TypeMapping IMAGE_GENERATIONS_B64_IMAGE_MAPPING = new TestUtil.TypeMapping(
            ImageGenerations.class,
            new TestUtil.TypeMapping[]{new TestUtil.TypeMapping(ImageBase64Generation.class, null)}
    );

    @Captor
    private ArgumentCaptor<WebRequest> webRequestArgumentCaptor;

    @ParameterizedTest
    @MethodSource("com.github.olson1998.openaiutil.model.req.ImageGenerationRequestTestModel#testImageGenerations")
    void shouldExecuteExpectedRequest(ImageGenerationRequest imageGenerationRequest){
        if(imageGenerationRequest instanceof ImageBase64GenerationRequest imageBase64GenerationRequest){
            verifyB64ImageUrlGenerationRequestExecuted(imageBase64GenerationRequest);
        } else if (imageGenerationRequest instanceof CustomImageGenerationRequest customImageGenerationRequest) {
            verifyCustomImageGenerationRequestExecuted(customImageGenerationRequest);
        }else {
            verifyImageGenerationRequestExpected(imageGenerationRequest);
        }
    }

    @Test
    void shouldSendRequestForB64Generation(){
        given(reactiveRestClient.sendHttpRequest(
                any(WebRequest.class),
                (ResponseMapping<ImageGenerations<ImageBase64Generation>>) argThat(arg -> TestUtil.isResponseMappingForType(arg, IMAGE_GENERATIONS_B64_IMAGE_MAPPING)))
        ).willReturn(Mono.just(TestUtil.webResponseOk(createTypedImageGeneration(new ImageBase64Generation[]{IMAGE_BASE_64_GENERATION}))));

        var response = chatGptReactiveClient().postB64ImageGenerationRequest(B_64_IMAGE_URL_GENERATION_REQUEST).block();

        then(reactiveRestClient).should().sendHttpRequest(webRequestArgumentCaptor.capture(), any(ResponseMapping.class));

        var webRequest = webRequestArgumentCaptor.getValue();
        assertThat(webRequest).isNotNull();
        assertThat(webRequest.httpMethod()).isEqualTo(POST);
        assertThat(webRequest.httpHeaders())
                .containsEntry(CONTENT_TYPE, List.of(APPLICATION_JSON.getMimeType()));
        assertThat(response).isNotNull();
        var imageGenerations = response.body();
        assertThat(imageGenerations).isNotNull();
        var imageGenerationsData =imageGenerations.getData();
        assertThat(imageGenerationsData).isNotNull().hasSize(1);
        var imageGeneration = imageGenerationsData[0];
        assertThat(imageGeneration).isNotNull();
        assertThat(imageGeneration.getB64Image()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("com.github.olson1998.openaiutil.model.req.ImageGenerationRequestTestModel#testImageGenerations")
    void shouldAlwaysSendRequestForDefaultImageGeneration(ImageGenerationRequest imageGenerationRequest){
        given(reactiveRestClient.sendHttpRequest(
                any(WebRequest.class),
                (ResponseMapping<ImageGenerations<DefaultImageGeneration>>) argThat(arg -> TestUtil.isResponseMappingForType(arg, IMAGE_GENERATIONS_DEFAULT_IMAGE_MAPPING)))
        ).willReturn(Mono.just(TestUtil.webResponseOk(createTypedImageGeneration(new DefaultImageGeneration[]{DEFAULT_IMAGE_GENERATION}))));

        var imageGenerationResponse = chatGptReactiveClient()
                .postDefaultImageGenerationRequest(imageGenerationRequest)
                .block();

        then(reactiveRestClient).should().sendHttpRequest(webRequestArgumentCaptor.capture(), any(ResponseMapping.class));

        var webRequest = webRequestArgumentCaptor.getValue();
        assertThat(webRequest).isNotNull();
        assertThat(webRequest.httpMethod()).isEqualTo(POST);
        assertThat(webRequest.httpHeaders())
                .containsEntry(CONTENT_TYPE, List.of(APPLICATION_JSON.getMimeType()));
        assertThat(imageGenerationResponse).isNotNull();
        var imageGeneration = imageGenerationResponse.body();
        assertThat(imageGeneration).isNotNull();
        var imageGenerations = imageGeneration.getData();
        assertThat(imageGenerations).isNotNull().hasSize(1);
        var generation = imageGenerations[0];
        assertThat(generation).isInstanceOf(DefaultImageGeneration.class);
    }

    @Test
    void shouldObtainImageGenerationAndDeserializeBase64Image(){
        given(reactiveRestClient.sendHttpRequest(
                any(WebRequest.class),
                (ResponseMapping<ImageGenerations<ImageGeneration>>) argThat(arg -> TestUtil.isResponseMappingForType(arg, IMAGE_GENERATIONS_B64_IMAGE_MAPPING)))
        ).willReturn(Mono.just(TestUtil.webResponseOk(IMAGE_BASE_64_IMAGE_GENERATIONS)));

        var imageDownloadsRef = new AtomicReference<List<ImageDownload>>();
        assertThatNoException().isThrownBy(()->{
            var imageDownloads =chatGptReactiveClient()
                    .postImageGenerationRequestAndObtain(B_64_IMAGE_URL_GENERATION_REQUEST)
                    .collectList()
                    .block();
            imageDownloadsRef.set(imageDownloads);
        });
        var imageDownloads = imageDownloadsRef.get();
        assertThat(imageDownloads).hasSize(1);
        var imageDownload = imageDownloads.get(0);
        assertThat(imageDownload).isNotNull();
        var imageRead = imageDownload.getImageRead();
        assertThat(imageRead).isNotNull();
        var image = imageRead.image();
        assertThat(image).isNotNull();
        var extension = imageRead.extension();
        assertThat(extension).isNotNull().isEqualTo(PNG);
        assertThat(imageDownload.getUri()).isNull();
    }

    @Test
    void shouldObtainImageGenerationAndCallUriToObtainImages(){
        var reactiveHttpRequestOrder = Mockito.inOrder(reactiveRestClient);
        given(reactiveRestClient.sendHttpRequest(
                any(WebRequest.class),
                (ResponseMapping<ImageGenerations<DefaultImageGeneration>>) argThat(arg -> TestUtil.isResponseMappingForType(arg, IMAGE_GENERATIONS_DEFAULT_IMAGE_MAPPING)))
        ).willReturn(Mono.just(TestUtil.webResponseOk(createTypedImageGeneration(new DefaultImageGeneration[]{DEFAULT_IMAGE_GENERATION}))));
        given(reactiveRestClient.sendHttpRequest(any(WebRequest.class), eq(BufferedImage.class)))
                .willReturn(Mono.just(TestUtil.webResponseOk(Base64ImageUtil.readBase64Image(IMAGE_BASE_64_GENERATION.getB64Image()).image())));

        var imageDownloads = chatGptReactiveClient().postImageGenerationRequestAndObtain(IMAGE_GENERATION_REQUEST)
                .collectList()
                .block();

        reactiveHttpRequestOrder.verify(reactiveRestClient).sendHttpRequest(any(WebRequest.class), any(ResponseMapping.class));
        reactiveHttpRequestOrder.verify(reactiveRestClient).sendHttpRequest(webRequestArgumentCaptor.capture(), eq(BufferedImage.class));

        assertThat(imageDownloads).isNotNull().hasSize(1);
        var imageDownload = imageDownloads.get(0);
        assertThat(imageDownload).isNotNull();
        var imageURI = imageDownload.getUri();
        assertThat(imageURI).isNotNull();
        var imageObtainWebRequest = webRequestArgumentCaptor.getValue();
        assertThat(imageObtainWebRequest).isNotNull();
        assertThat(imageObtainWebRequest.httpMethod()).isEqualTo(GET);
        assertThat(imageObtainWebRequest.uri()).isEqualTo(imageURI);
    }

    private void verifyB64ImageUrlGenerationRequestExecuted(ImageBase64GenerationRequest imageBase64GenerationRequest){
        given(reactiveRestClient.sendHttpRequest(
                any(WebRequest.class),
                (ResponseMapping<ImageGenerations<ImageGeneration>>) argThat(arg -> TestUtil.isResponseMappingForType(arg, IMAGE_GENERATIONS_B64_IMAGE_MAPPING)))
        ).willReturn(Mono.just(TestUtil.webResponseOk(IMAGE_BASE_64_IMAGE_GENERATIONS)));

        var imageGenerationWebResponse = chatGptReactiveClient()
                .postImageGenerationRequest(imageBase64GenerationRequest)
                .block();

        then(reactiveRestClient).should().sendHttpRequest(webRequestArgumentCaptor.capture(), any(ResponseMapping.class));

        var webRequest = webRequestArgumentCaptor.getValue();
        assertThat(webRequest).isNotNull();
        assertThat(webRequest.httpMethod()).isEqualTo(POST);
        assertThat(webRequest.httpHeaders())
                .containsEntry(CONTENT_TYPE, List.of(APPLICATION_JSON.getMimeType()));
        assertThat(imageGenerationWebResponse).isNotNull();
        var imageGeneration = imageGenerationWebResponse.body();
        assertThat(imageGeneration).isNotNull();
        var imageGenerations = imageGeneration.getData();
        assertThat(imageGenerations).isNotNull().hasSize(1);
        var generation = imageGenerations[0];
        assertThat(generation).isInstanceOf(ImageBase64Generation.class);
    }

    private void verifyCustomImageGenerationRequestExecuted(CustomImageGenerationRequest customImageGenerationRequest){
        given(reactiveRestClient.sendHttpRequest(
                any(WebRequest.class),
                (ResponseMapping<ImageGenerations<ImageGeneration>>) argThat(arg -> TestUtil.isResponseMappingForType(arg, IMAGE_GENERATIONS_B64_IMAGE_MAPPING)))
        ).willReturn(Mono.just(TestUtil.webResponseOk(IMAGE_BASE_64_IMAGE_GENERATIONS)));

        var imageGenerationWebResponse = chatGptReactiveClient().postImageGenerationRequest(customImageGenerationRequest).block();

        then(reactiveRestClient).should().sendHttpRequest(webRequestArgumentCaptor.capture(), any(ResponseMapping.class));

        var webRequest = webRequestArgumentCaptor.getValue();
        assertThat(webRequest).isNotNull();
        assertThat(webRequest.httpMethod()).isEqualTo(POST);
        assertThat(webRequest.httpHeaders())
                .containsEntry(CONTENT_TYPE, List.of(APPLICATION_JSON.getMimeType()));
        assertThat(imageGenerationWebResponse).isNotNull();
        var imageGeneration = imageGenerationWebResponse.body();
        assertThat(imageGeneration).isNotNull();
        var imageGenerations = imageGeneration.getData();
        assertThat(imageGenerations).isNotNull().hasSize(1);
        var generation = imageGenerations[0];
        assertThat(generation).isInstanceOf(ImageBase64Generation.class);
    }

    private void verifyImageGenerationRequestExpected(ImageGenerationRequest imageGenerationRequest){
        given(reactiveRestClient.sendHttpRequest(
                any(WebRequest.class),
                (ResponseMapping<ImageGenerations<ImageGeneration>>) argThat(arg -> TestUtil.isResponseMappingForType(arg, IMAGE_GENERATIONS_DEFAULT_IMAGE_MAPPING))
        )).willReturn(Mono.just(TestUtil.webResponseOk(DEFAULT_IMAGE_IMAGE_GENERATIONS)));

        var imageGenerationResponse = chatGptReactiveClient()
                .postImageGenerationRequest(imageGenerationRequest)
                .block();

        then(reactiveRestClient).should().sendHttpRequest(webRequestArgumentCaptor.capture(), any(ResponseMapping.class));

        var webRequest = webRequestArgumentCaptor.getValue();
        assertThat(webRequest).isNotNull();
        assertThat(webRequest.httpMethod()).isEqualTo(POST);
        assertThat(webRequest.httpHeaders())
                .containsEntry(CONTENT_TYPE, List.of(APPLICATION_JSON.getMimeType()));
        assertThat(imageGenerationResponse).isNotNull();
        var imageGeneration = imageGenerationResponse.body();
        assertThat(imageGeneration).isNotNull();
        var imageGenerations = imageGeneration.getData();
        assertThat(imageGenerations).isNotNull().hasSize(1);
        var generation = imageGenerations[0];
        assertThat(generation).isInstanceOf(DefaultImageGeneration.class);
    }
}
