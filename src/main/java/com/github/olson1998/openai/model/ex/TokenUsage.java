package com.github.olson1998.openai.model.ex;

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
    public TokenUsage(@JsonProperty(value = "prompt_tokens") Long promptTokens,
                      @JsonProperty(value = "completion_tokens") Long completionTokens,
                      @JsonProperty(value = "total_tokens") Long totalTokens) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }
}
