package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class Choice {

    private final Integer index;

    private final Message message;

    private final LogProb logProbs;

    @JsonProperty(value = "finish_reason")
    private final String finishReason;

    @JsonCreator
    public Choice(@JsonProperty(value = "index") Integer index,
                  @JsonProperty(value = "message") Message message,
                  @JsonProperty(value = "logprobs") LogProb logProbs,
                  @JsonProperty(value = "finish_reason") String finishReason) {
        this.index = index;
        this.message = message;
        this.logProbs = logProbs;
        this.finishReason = finishReason;
    }
}
