package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
public class ImageGenerations<I extends ImageGeneration> {

    private final Instant creationTimestamp;

    private final List<I> data;

    @JsonCreator
    public ImageGenerations(@JsonProperty(value = "created") Long creationTimestamp,
                            @JsonProperty(value = "data") I[] data) {
        this.creationTimestamp = Optional.ofNullable(creationTimestamp)
                .map(epochMilli -> epochMilli * 1000)
                .map(Instant::ofEpochMilli)
                .orElse(null);
        this.data = Optional.ofNullable(data)
                .map(List::of)
                .orElse(null);
    }
}
