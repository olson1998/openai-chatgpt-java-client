package com.github.olson1998.openaiutil.model.req;

import com.github.olson1998.openaiutil.model.chat.ChatModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import com.github.olson1998.openaiutil.model.ex.Message;
import com.github.olson1998.openaiutil.model.ex.UserMessage;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ChatRequestTestModel {

    public static final List<Message> MESSAGES = List.of(new UserMessage("This is test"));

    public static final ChatRequest CHAT_REQUEST = ChatRequest.builder()
            .model(ChatModel.GPT40613)
            .responseFormat(ResponseFormat.JSON_OBJECT)
            .messages(MESSAGES)
            .build();

}
