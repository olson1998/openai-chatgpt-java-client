package com.github.olson1998.openaiutil.model.response;

import com.github.olson1998.openaiutil.model.request.Message;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.github.olson1998.openaiutil.model.chat.RoleAttribute.USER;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserMessage extends Message {

    public UserMessage(String content) {
        super(USER, content);
    }
}
