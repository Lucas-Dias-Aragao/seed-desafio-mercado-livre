package com.dev.eficiente.desafio.marketplace.model.vo;


import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PerguntaRequestVo(

        @NotBlank(message = MessageConstants.PERGUNTA_INVALIDA)
        @Size(min = 10, max = 500)
        String pergunta
) {}
