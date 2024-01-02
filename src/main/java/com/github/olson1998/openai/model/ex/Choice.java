package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {

    private Integer index;

    private Message message;

    private LogProb logprobs;

    @JsonProperty(value = "finish_reason")
    private String finishReason;


}
