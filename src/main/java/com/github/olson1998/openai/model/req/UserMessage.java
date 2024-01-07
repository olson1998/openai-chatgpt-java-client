package com.github.olson1998.openai.model.req;

import com.github.olson1998.openai.model.ex.Message;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.github.olson1998.openai.model.chat.RoleAttribute.USER;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserMessage extends Message {

    public UserMessage(String content) {
        super(USER, content);
    }
}
