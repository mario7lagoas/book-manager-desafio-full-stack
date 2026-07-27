package com.labs.vgx.books.builders;

import com.labs.vgx.books.models.ErroResponse;
import com.labs.vgx.books.models.ErrorApiResponse;

import java.util.List;

public class ErrorApiResponseBuilder {
    private ErrorApiResponse errorApiResponse;

    private ErrorApiResponseBuilder() {
    }

    public static ErrorApiResponseBuilder builder() {
        ErrorApiResponseBuilder builder = new ErrorApiResponseBuilder();
        builder.errorApiResponse = new ErrorApiResponse();
        return builder;
    }

    public ErrorApiResponseBuilder status(String string) {
        errorApiResponse.status(string);
        return this;
    }

    public ErrorApiResponseBuilder erros(List<ErroResponse> erros) {
        errorApiResponse.erros(erros);
        return this;
    }

    public ErrorApiResponse build() {
        return errorApiResponse;
    }
}
