package com.github.olson1998.openai.model.ex;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.github.olson1998.openai.model.chat.RoleAttribute.system;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SystemMessage extends Message {

    public SystemMessage(String content) {
        super(system, content);
    }
}
