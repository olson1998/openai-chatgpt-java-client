package com.github.olson1998.openaiutil.model.chat;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * The role attribute in the model you've referenced plays a crucial role in distinguishing between different types of messages in a conversation.
 * In the context of the OpenAI API, especially when using the chat completion models, messages are treated as part of a dialogue, and the role attribute helps the model understand the nature of each message.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleAttribute {

    system,
    user,
    assistant

}
