package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ImageBase64Generation extends ImageGeneration {

    @JsonProperty(value = "b64_json")
    private final String b64Image;

    @JsonCreator
    public ImageBase64Generation(@JsonProperty(value = "revised_prompt")String revisedPrompt,
                                 @JsonProperty(value = "b64_json") String b64Image) {
        super(revisedPrompt);
        this.b64Image = b64Image;
    }
}
