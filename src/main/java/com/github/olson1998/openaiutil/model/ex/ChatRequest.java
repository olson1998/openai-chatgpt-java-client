package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openaiutil.model.chat.ChatModel;
import com.github.olson1998.openaiutil.model.chat.ResponseFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    @JsonProperty(value = "model", required = true)
    private ChatModel model;

    @JsonProperty(value = "response_format")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseFormat responseFormat;

    @JsonProperty(value = "messages", required = true)
    private List<Message> messages;

}
