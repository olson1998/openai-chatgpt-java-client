package com.github.olson1998.openaiutil.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
public class LogProb {

    private final String[] tokens;

    private final double[] tokenLogprobs;

    private final Map<String, Double> topLogprobs;

    private final Integer[] textOffsets;

    @JsonCreator
    public LogProb(@JsonProperty(value = "tokens") String[] tokens,
                   @JsonProperty(value = "token_logprobs") double[] tokenLogprobs,
                   @JsonProperty(value = "top_logprobs") Map<String, Double> topLogprobs,
                   @JsonProperty(value = "text_offsets") Integer[] textOffsets) {
        this.tokens = tokens;
        this.tokenLogprobs = tokenLogprobs;
        this.topLogprobs = topLogprobs;
        this.textOffsets = textOffsets;
    }
}
