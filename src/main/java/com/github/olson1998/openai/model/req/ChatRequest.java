package com.github.olson1998.openai.model.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openai.model.chat.ChatModel;
import com.github.olson1998.openai.model.chat.ResponseFormat;
import com.github.olson1998.openai.model.ex.Message;
import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode

@Builder
@AllArgsConstructor
public class ChatRequest {

    @JsonProperty(value = "model", required = true)
    private final ChatModel model;

    @JsonProperty(value = "response_format")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final ResponseFormat responseFormat;

    @JsonProperty(value = "messages", required = true)
    private final List<Message> messages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Double temperature;

    @JsonProperty(value = "max_tokens")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer maxTokens;

    @JsonProperty(value = "top_p")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer topP;
}
