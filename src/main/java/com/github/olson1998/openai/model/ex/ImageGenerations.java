package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerations<I extends ImageGeneration> {

    @JsonProperty(value = "created")
    private Long creationTimestamp;

    private I[] data;
}
