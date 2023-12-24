package com.github.olson1998.openaiutil.client;

import com.github.olson1998.openaiutil.model.ex.ChatRequest;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;

public interface ChatGptClient {

    ChatCompletion postChatRequest(ChatRequest chatRequest);

}
