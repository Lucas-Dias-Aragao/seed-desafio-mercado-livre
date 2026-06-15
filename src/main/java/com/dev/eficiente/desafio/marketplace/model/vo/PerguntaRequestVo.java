package com.dev.eficiente.desafio.marketplace.model.vo;


import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PerguntaRequestVo(

        @NotBlank(message = MessageConstants.TAMANHO_PERGUNTA_INVALIDA)
        @Size(min = 10, max = 500, message = MessageConstants.TAMANHO_PERGUNTA_INVALIDA)
        String pergunta
) {}
