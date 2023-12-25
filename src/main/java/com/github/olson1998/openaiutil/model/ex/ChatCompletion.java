package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletion {

    private String id;

    private String object;

    @JsonProperty(value = "created")
    private Long creationTimestamp;

    private String model;

    private Choice[] choices;

    @JsonProperty(value = "usage")
    private TokenUsage tokenUsage;

    @JsonProperty(value = "system_fingerprint")
    private String systemFingerprint;

}
