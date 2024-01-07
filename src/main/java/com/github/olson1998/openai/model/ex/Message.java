package com.github.olson1998.openai.model.ex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openai.model.chat.RoleAttribute;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class Message {

    private final RoleAttribute role;

    private final String content;

    @JsonCreator
    public Message(@JsonProperty(value = "role") RoleAttribute role,
                   @JsonProperty(value = "content") String content) {
        this.role = role;
        this.content = content;
    }
}
