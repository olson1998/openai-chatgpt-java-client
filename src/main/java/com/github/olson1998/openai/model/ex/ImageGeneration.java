package com.github.olson1998.openai.model.ex;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public abstract class ImageGeneration {

    private final String revisedPrompt;

}
