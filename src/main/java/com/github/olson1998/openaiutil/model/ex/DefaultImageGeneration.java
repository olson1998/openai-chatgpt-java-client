package com.github.olson1998.openaiutil.model.ex;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DefaultImageGeneration extends ImageGeneration{

    private URI url;
}
