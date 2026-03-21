package com.dev.eficiente.desafio.marketplace.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CategoriaNotFoundException extends BusinessException {

    private String mensagem;

    private HttpStatus status;

    public CategoriaNotFoundException(String mensagem, HttpStatus status) {
        super(mensagem, status);
    }

    public CategoriaNotFoundException() {
        this("Categoria não encontrada", HttpStatus.NOT_FOUND);
    }

}
