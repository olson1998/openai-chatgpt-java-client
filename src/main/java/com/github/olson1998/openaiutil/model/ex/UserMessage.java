package com.github.olson1998.openaiutil.model.ex;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.github.olson1998.openaiutil.model.chat.RoleAttribute.user;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserMessage extends Message {

    public UserMessage(String content) {
        super(user, content);
    }
}
