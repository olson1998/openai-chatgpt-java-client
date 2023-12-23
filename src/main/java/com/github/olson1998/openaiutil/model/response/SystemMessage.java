package com.github.olson1998.openaiutil.model.response;

import com.github.olson1998.openaiutil.model.request.Message;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.github.olson1998.openaiutil.model.chat.RoleAttribute.SYSTEM;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SystemMessage extends Message {

    public SystemMessage(String content) {
        super(SYSTEM, content);
    }
}
