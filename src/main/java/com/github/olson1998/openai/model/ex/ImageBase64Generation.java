package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@NoArgsConstructor
public class ImageBase64Generation extends ImageGeneration {

    @JsonProperty(value = "b64_json")
    private String b64Image;

    public ImageBase64Generation(String revisedPrompt, String b64Image) {
        super(revisedPrompt);
        this.b64Image = b64Image;
    }
}
