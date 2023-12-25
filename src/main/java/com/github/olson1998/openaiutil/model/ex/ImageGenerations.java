package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerations {

    @JsonProperty(value = "created")
    private Long creationTimestamp;

    private ImageGeneration[] data;
}
