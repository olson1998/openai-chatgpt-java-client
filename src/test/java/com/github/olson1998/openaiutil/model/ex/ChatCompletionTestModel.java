package com.github.olson1998.openaiutil.model.ex;

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
            MODEL,
            null,
            TOKEN_USAGE,
            null
    );
}
