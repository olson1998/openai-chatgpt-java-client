package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class ImageGeneration {

    @JsonProperty(value = "revised_prompt")
    private String revisedPrompt;
}
