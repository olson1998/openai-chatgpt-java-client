package com.github.olson1998.openaiutil.model.chat;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The role attribute in the model you've referenced plays a crucial role in distinguishing between different types of messages in a conversation.
 * In the context of the OpenAI API, especially when using the chat completion models, messages are treated as part of a dialogue, and the role attribute helps the model understand the nature of each message.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleAttribute {

    /**
     * "system": This role is used for messages that provide instructions or contextual information to the model.
     * It's like setting the stage or providing guidelines for how the model should interpret and respond to the user's queries.
     * For example, "You are a helpful assistant designed to output JSON." is a system message that sets the context for the model's behavior.
     */
    SYSTEM("system"),
    /**
     * "user": This role is assigned to messages that simulate what a real user would say.
     * In your example, the user's query is "Who won the world series in 2020?" This message is treated as the input from the end-user to which the model needs to respond.
     */
    USER("user");

    private final String role;

    @JsonValue
    public String getRole() {
        return role;
    }
}
