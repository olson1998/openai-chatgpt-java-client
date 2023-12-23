package com.github.olson1998.openaiutil.model.chat;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChatModel {

    GPT40613("gpt-4-0613");

    private final String model;

    @JsonValue
    public String getModel() {
        return model;
    }
}
