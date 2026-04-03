package com.dev.eficiente.desafio.marketplace.model.dto;

import lombok.Getter;

public class ResponseMessageDto {

    @Getter
    private String mensagem;

    public ResponseMessageDto(final String mensagem) {
        this.mensagem = mensagem;
    }
}
