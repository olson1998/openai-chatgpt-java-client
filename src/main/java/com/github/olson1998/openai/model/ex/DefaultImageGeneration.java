package com.github.olson1998.openai.model.ex;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@NoArgsConstructor
public class DefaultImageGeneration extends ImageGeneration{

    private URI url;

    public DefaultImageGeneration(String revisedPrompt, URI url) {
        super(revisedPrompt);
        this.url = url;
    }
}
