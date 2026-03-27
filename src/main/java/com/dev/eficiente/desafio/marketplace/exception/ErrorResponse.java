package com.dev.eficiente.desafio.marketplace.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
public class ErrorResponse {

    @Getter
    private String mensagem;

    @Getter
    private HttpStatus statusCode;

    public ErrorResponse(final String mensagem) {
        this.mensagem = mensagem;
    }

}
