package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AvaliacaoRequestVo(

        @NotNull(message = MessageConstants.QTD_ESTRELA_INVALIDA)
        @Min(value = 1, message = MessageConstants.QTD_ESTRELA_INVALIDA)
        @Max(value = 5, message = MessageConstants.QTD_ESTRELA_INVALIDA)
        Short estrela,

        @NotBlank(message = MessageConstants.TITULO_INVALIDO)
        String titulo,

        @Size(min = 10, max = 500, message = MessageConstants.DESCRICAO_AVALIACAO_INVALIDA)
        String descricao

) {}
