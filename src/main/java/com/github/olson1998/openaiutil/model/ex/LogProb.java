package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogProb {

    private String[] tokens;

    @JsonProperty(value = "token_logprobs")
    private double[] tokenLogprobs;

    @JsonProperty(value = "top_logprobs")
    private Map<String, Double> topLogprobs;

    @JsonProperty(value = "text_offsets")
    private Integer[] textOffsets;

    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> unmappedProperties;
}
