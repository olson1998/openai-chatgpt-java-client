package com.github.olson1998.openaiutil.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openaiutil.model.request.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Choice {

    private final Integer index;

    private final Message message;

    private final LogProb logprobs;

    @JsonProperty(value = "finish_reason")
    private final String finishReason;

    @JsonCreator
    public Choice(@JsonProperty(value = "index", required = true) Integer index,
                  @JsonProperty(value = "message", required = true) Message message,
                  @JsonProperty(value = "logprobs") LogProb logprobs,
                  @JsonProperty(value = "finish_reason", required = true) String finishReason) {
        this.index = index;
        this.message = message;
        this.logprobs = logprobs;
        this.finishReason = finishReason;
    }
}
