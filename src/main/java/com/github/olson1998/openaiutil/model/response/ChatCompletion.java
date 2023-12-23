package com.github.olson1998.openaiutil.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ChatCompletion {

    private final String id;

    private final String object;

    private final Long creationTimestamp;

    private final String model;

    private final Choice[] choices;

    @JsonProperty(value = "usage")
    private final TokenUsage tokenUsage;

    @JsonCreator
    public ChatCompletion(@JsonProperty(value = "id", required = true) String id,
                          @JsonProperty(value = "object", required = true) String object,
                          @JsonProperty(value = "creationTimestamp", required = true) Long creationTimestamp,
                          @JsonProperty(value = "model", required = true) String model,
                          @JsonProperty(value = "choices", required = true) Choice[] choices,
                          @JsonProperty(value = "usage", required = true) TokenUsage tokenUsage) {
        this.id = id;
        this.object = object;
        this.creationTimestamp = creationTimestamp;
        this.model = model;
        this.choices = choices;
        this.tokenUsage = tokenUsage;
    }
}
