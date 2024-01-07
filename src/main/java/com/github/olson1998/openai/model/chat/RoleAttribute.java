package com.github.olson1998.openai.model.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleAttribute {

    public static final RoleAttribute USER = new RoleAttribute("user");

    public static final RoleAttribute SYSTEM = new RoleAttribute("system");

    public static final RoleAttribute ASSISTANT = new RoleAttribute("assistant");

    public static final Set<RoleAttribute> ROLE_ATTRIBUTES = Set.of(
            USER,
            SYSTEM,
            ASSISTANT
    );

    private final String name;

    @JsonCreator
    @JsonDeserialize(using = StringDeserializer.class)
    public static RoleAttribute forName(String name){
        return ROLE_ATTRIBUTES.stream()
                .filter(roleAttribute -> roleAttribute.name.equals(name))
                .findFirst()
                .orElseThrow();
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
