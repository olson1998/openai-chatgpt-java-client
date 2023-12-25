package com.github.olson1998.openaiutil.model.chat;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DalleModel {

    DALLE2("dall-e-2"),
    DALLE3("dall-e-3");
    private final String model;

    @JsonValue
    public String getModel() {
        return model;
    }
}
