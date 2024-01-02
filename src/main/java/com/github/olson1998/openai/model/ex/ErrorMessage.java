package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private String message;

    private String type;

    private String code;

    @JsonProperty(value = "param")
    private Object parameter;

    @JsonAnySetter
    @ToString.Exclude
    private Map<String, Object> unmappedProperties;
}
