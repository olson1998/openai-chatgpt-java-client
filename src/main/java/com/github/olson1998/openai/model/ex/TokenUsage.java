package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenUsage {

    @JsonProperty(value = "prompt_tokens")
    private Long promptTokens;

    @JsonProperty(value = "completion_tokens")
    private Long completionTokens;

    @JsonProperty(value = "total_tokens")
    private Long totalTokens;

}
