package com.github.olson1998.openai.model.ex;

import com.github.olson1998.openai.model.chat.ChatModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatCompletionTestModel {

    public static final String ID = "sk-some-random-id";

    public static final String MODEL = "text-davinci-mock";

    public static final TokenUsage TOKEN_USAGE =new TokenUsage(1L, 1L, 1L);

    public static final ChatCompletion CHAT_COMPLETION = new ChatCompletion(
            ID,
            null,
            System.currentTimeMillis(),
            ChatModel.GPT40613,
            null,
            TOKEN_USAGE,
            null
    );
}
