package com.github.olson1998.openai.model.req;

import com.github.olson1998.openai.model.ex.Message;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.github.olson1998.openai.model.chat.RoleAttribute.SYSTEM;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SystemMessage extends Message {

    public SystemMessage(String content) {
        super(SYSTEM, content);
    }
}
