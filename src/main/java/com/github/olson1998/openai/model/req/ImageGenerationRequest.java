package com.github.olson1998.openai.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openai.model.chat.DalleModel;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode

@Builder
@AllArgsConstructor
public class ImageGenerationRequest {

    private final DalleModel model;

    private final String prompt;

    private final String size;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String quality;

    @JsonProperty(value = "n")
    private final Integer numberOfImages;
}
