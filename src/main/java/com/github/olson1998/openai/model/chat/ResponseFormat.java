package com.github.olson1998.openai.model.chat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFormat {

    public static final ResponseFormat JSON_OBJECT = new ResponseFormat("json_object");

    public static final ResponseFormat B64JSON = new ResponseFormat("b64_json");

    private final String type;

}
