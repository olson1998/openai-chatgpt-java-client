package com.github.olson1998.openaiutil.client;

import com.github.olson1998.openaiutil.model.response.ChatCompletion;
import com.github.olson1998.openaiutil.model.request.ChatRequest;

public interface ChatCompletionClient {

    ChatCompletion getChatCompletion(ChatRequest chatRequest);
}
