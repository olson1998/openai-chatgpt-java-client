package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageGeneration {

    @JsonProperty(value = "revised_prompt")
    private String revisedPrompt;

    private URI url;
}
