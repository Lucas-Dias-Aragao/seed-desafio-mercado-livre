package com.dev.eficiente.desafio.marketplace.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private String mensagem;

    private HttpStatus status;

}
