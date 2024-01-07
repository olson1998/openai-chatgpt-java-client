package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class Error {

    private final ErrorMessage message;

    @JsonCreator
    public Error(@JsonProperty(value = "error") ErrorMessage message) {
        this.message = message;
    }
}
