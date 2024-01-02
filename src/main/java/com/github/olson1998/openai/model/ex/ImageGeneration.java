package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ImageGeneration {

    @JsonProperty(value = "revised_prompt")
    private String revisedPrompt;
}
