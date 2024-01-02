package com.github.olson1998.openai.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConst {

    public static final String AUTHORIZATION_TOKEN = "TOKEN";

    private static final String BEARER_TOKEN = "Bearer " + AUTHORIZATION_TOKEN;

    public static final String OPENAI_BASE_URI = "http://openai.mock/v1.0";

    public static final String CHAT_COMPLETION_PATH = "/chat/completion";

    public static final String IMAGES_GENERATION_PATH = "/image/generation";

}
