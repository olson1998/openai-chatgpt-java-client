package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class LogProb {

    private final List<String> tokens;

    @JsonProperty(value = "token_logprobs")
    private final List<Double> tokenLogprobs;

    @JsonProperty(value = "top_logprobs")
    private final Map<String, Double> topLogProbs;

    @JsonProperty(value = "text_offsets")
    private final List<Integer> textOffsets;

    @JsonCreator
    public LogProb(@JsonProperty(value = "tokens") String[] tokens,
                   @JsonProperty(value = "token_logprobs") Double[] tokenLogProbs,
                   @JsonProperty(value = "top_logprobs") Map<String, Double> topLogProbs,
                   @JsonProperty(value = "text_offsets") Integer[] textOffsets) {
        this.tokens = Optional.ofNullable(tokens)
                .map(List::of)
                .orElse(null);
        this.tokenLogprobs = Optional.ofNullable(tokenLogProbs)
                .map(List::of)
                .orElse(null);
        this.topLogProbs = Optional.ofNullable(topLogProbs)
                .map(Collections::unmodifiableMap)
                .orElse(null);
        this.textOffsets = Optional.ofNullable(textOffsets)
                .map(List::of)
                .orElse(null);
    }


}
