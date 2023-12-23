package com.github.olson1998.openaiutil.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class TokenUsage {

    @JsonProperty(value = "prompt_tokens")
    private final Long promptTokens;

    @JsonProperty(value = "completion_tokens")
    private final Long completionTokens;

    @JsonProperty(value = "total_tokens")
    private final Long totalTokens;

    @JsonCreator
    public TokenUsage(@JsonProperty(value = "prompt_tokens", required = true) Long promptTokens,
                      @JsonProperty(value = "completion_tokens", required = true) Long completionTokens,
                      @JsonProperty(value = "total_tokens", required = true) Long totalTokens) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }
}
