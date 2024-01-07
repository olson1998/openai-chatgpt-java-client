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
public final class DalleModel {

    public static final DalleModel DALLE2 = new DalleModel("dall-e-2");

    public static final DalleModel DALLE3 = new DalleModel("dall-e-3");

    public static final Set<DalleModel> DALLE_MODELS = Set.of(
            DALLE2,
            DALLE3
    );

    private final String name;

    @JsonCreator
    @JsonDeserialize(using = StringDeserializer.class)
    public static DalleModel forName(@NonNull String name){
        return DALLE_MODELS.stream()
                .filter(dalleModel -> dalleModel.name.equals(name))
                .findFirst()
                .orElseThrow();
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
