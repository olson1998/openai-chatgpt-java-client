package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.net.URI;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ImageURLGeneration extends ImageGeneration{

    private final URI url;

    @JsonCreator
    public ImageURLGeneration(@JsonProperty(value = "revised_prompt")String revisedPrompt,
                              @JsonProperty(value = "url") URI url) {
        super(revisedPrompt);
        this.url = url;
    }
}
