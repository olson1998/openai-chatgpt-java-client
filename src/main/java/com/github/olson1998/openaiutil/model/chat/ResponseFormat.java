package com.github.olson1998.openaiutil.model.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseFormat {

    JSON_OBJECT("json_object");

    private final String type;
}
