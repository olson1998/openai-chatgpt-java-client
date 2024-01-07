package com.github.olson1998.openai.model.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import lombok.*;

import java.util.Set;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChatModel {

    public static final ChatModel GPT35TURBO = new ChatModel("gpt-3.5-turbo");

    public static final ChatModel GPT35TURBO16k = new ChatModel("gpt-3.5-turbo-16k");

    public static final ChatModel GPT35TURBO1106 = new ChatModel("gpt-3.5-turbo-1106");

    public static final ChatModel GPT4 = new ChatModel("gpt-4");

    public static final ChatModel GPT40613 = new ChatModel("gpt-4-0613");
    public static final Set<ChatModel> CHAT_MODELS = Set.of(
            GPT35TURBO,
            GPT35TURBO16k,
            GPT35TURBO1106,
            GPT4,
            GPT40613
    );

    private final String name;

    @JsonCreator
    @JsonDeserialize(using = StringDeserializer.class)
    public static ChatModel forName(@NonNull String name){
        return CHAT_MODELS.stream()
                .filter(chatModel -> chatModel.name.equals(name))
                .findFirst()
                .orElseThrow();
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
