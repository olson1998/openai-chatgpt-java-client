package com.github.olson1998.openaiutil.model.ex;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorTestModel {

    public static final String ERROR_MESSAGE_VALUE = "Mocked error";

    public static final String ERROR_TYPE = "invalid_request_error";

    public static final ErrorMessage ERROR_MESSAGE = new ErrorMessage(
            ERROR_MESSAGE_VALUE,
            ERROR_TYPE,
            null,
            null,
            null
    );

    public static final Error ERROR  =new Error(ERROR_MESSAGE);

    public static final byte[] ERROR_BYTES = serializeError(ERROR);

    @SneakyThrows
    private byte[] serializeError(Error error){
        return new ObjectMapper().writeValueAsBytes(error);
    }
}
