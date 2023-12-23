package com.github.olson1998.openaiutil.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openaiutil.model.chat.RoleAttribute;
import lombok.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Message {

    @JsonProperty(value = "role", required = true)
    private final RoleAttribute role;

    @JsonProperty(value = "content", required = true)
    private String content;

}
