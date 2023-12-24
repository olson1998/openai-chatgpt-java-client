package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openaiutil.model.chat.ChatModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    @JsonProperty(value = "model", required = true)
    private ChatModel model;

    @JsonProperty(value = "messages", required = true)
    private LinkedList<Message> messages;

}
