package com.github.olson1998.openai.model.req;

import com.github.olson1998.openai.model.chat.DalleModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.github.olson1998.openai.model.chat.ResponseFormat.B64JSON;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ImageBase64GenerationRequest extends CustomImageGenerationRequest{

    @Builder(builderMethodName = "b64Image")
    public ImageBase64GenerationRequest(DalleModel model, String prompt, String size, String quality, Integer numberOfImages) {
        super(model, prompt, size, quality, numberOfImages, B64JSON);
    }
}
