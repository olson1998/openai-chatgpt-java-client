package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class ErrorMessage {

    private final String message;

    private final String type;

    private final String code;

    @JsonProperty(value = "param")
    private final Object parameter;

    @JsonCreator
    public ErrorMessage(@JsonProperty(value = "message") String message,
                        @JsonProperty(value = "type") String type,
                        @JsonProperty(value = "code") String code,
                        @JsonProperty(value = "parameter") Object parameter) {
        this.message = message;
        this.type = type;
        this.code = code;
        this.parameter = parameter;
    }
}
