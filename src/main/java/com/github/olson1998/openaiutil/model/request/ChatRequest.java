package com.github.olson1998.openaiutil.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.olson1998.openaiutil.model.chat.ChatModel;
import lombok.*;

import java.util.LinkedList;
import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class ChatRequest {

    @JsonProperty(value = "model", required = true)
    private final ChatModel model;

    @JsonProperty(value = "messages", required = true)
    private final LinkedList<Message> messages;

    @JsonCreator
    public ChatRequest(@JsonProperty(value = "model") ChatModel model,
                       @JsonProperty(value = "messages") LinkedList<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public static Builder builder(){
        return new Builder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder{

        private ChatModel model;

        private LinkedList<Message> messages = new LinkedList<>();

        public Builder model(ChatModel model){
            this.model = model;
            return this;
        }

        public Builder nextMessage(Message message){
            messages.add(message);
            return this;
        }

        public ChatRequest build(){
            Objects.requireNonNull(model, "Chat request model has not been specified");
            return new ChatRequest(model, messages);
        }
    }
}
