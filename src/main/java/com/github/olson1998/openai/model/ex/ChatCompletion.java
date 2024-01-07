package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openai.model.chat.ChatModel;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class ChatCompletion {

    private final String id;

    private final String object;

    @JsonProperty(value = "created")
    private final Instant creationTimestamp;

    private final ChatModel model;

    private final List<Choice> choices;

    @JsonProperty(value = "usage")
    private final TokenUsage tokenUsage;

    @JsonProperty(value = "system_fingerprint")
    private final String systemFingerprint;

    @JsonCreator
    public ChatCompletion(@JsonProperty(value = "id") String id,
                          @JsonProperty(value = "object") String object,
                          @JsonProperty(value = "created") Long creationTimestamp,
                          @JsonProperty(value = "model") ChatModel model,
                          @JsonProperty(value = "choices") Choice[] choices,
                          @JsonProperty(value = "usage") TokenUsage tokenUsage,
                          @JsonProperty(value = "system_fingerprint") String systemFingerprint) {
        this.id = id;
        this.object = object;
        this.creationTimestamp = Optional.ofNullable(creationTimestamp)
                .map(epochMilli -> epochMilli * 1000)
                .map(Instant::ofEpochMilli)
                .orElse(null);
        this.model = model;
        this.choices = Optional.ofNullable(choices)
                .map(List::of)
                .orElse(null);
        this.tokenUsage = tokenUsage;
        this.systemFingerprint = systemFingerprint;
    }
}
