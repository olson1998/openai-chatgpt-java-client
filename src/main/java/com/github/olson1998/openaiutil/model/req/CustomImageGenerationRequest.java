package com.github.olson1998.openaiutil.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openaiutil.model.chat.DalleModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomImageGenerationRequest extends ImageGenerationRequest{

    @JsonProperty(value = "response_format")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String responseFormat;

    @Builder(builderMethodName = "custom")
    public CustomImageGenerationRequest(DalleModel model, String prompt, String size, String quality, Integer numberOfImages, ResponseFormat responseFormat) {
        super(model, prompt, size, quality, numberOfImages);
        this.responseFormat = responseFormat.getType();
    }

}
