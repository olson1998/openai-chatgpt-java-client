package com.github.olson1998.openaiutil.model.ex;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestError {

    private RequestError.Message message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message{

        private String message;

        private String type;

        private String code;

        private Object parameter;
    }
}
