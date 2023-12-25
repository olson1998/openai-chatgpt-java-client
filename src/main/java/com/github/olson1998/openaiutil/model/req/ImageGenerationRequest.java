package com.github.olson1998.openaiutil.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openaiutil.model.chat.DalleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerationRequest {

    private DalleModel model;

    private String prompt;

    private String size;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String quality;

    @JsonProperty(value = "n")
    private Integer numberOfImages;
}
