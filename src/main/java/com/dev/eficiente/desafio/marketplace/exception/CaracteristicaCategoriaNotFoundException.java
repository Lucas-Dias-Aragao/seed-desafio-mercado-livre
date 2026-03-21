package com.dev.eficiente.desafio.marketplace.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CaracteristicaCategoriaNotFoundException extends BusinessException {

    public CaracteristicaCategoriaNotFoundException(String mensagem, HttpStatus status) {
        super(mensagem, status);
    }

    public CaracteristicaCategoriaNotFoundException() {
        this("Caracteristica não encontrada", HttpStatus.NOT_FOUND);
    }

}
