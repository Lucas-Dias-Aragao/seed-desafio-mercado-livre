package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.model.entity.ProdutoCaracteristica;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import com.dev.eficiente.desafio.marketplace.validation.annotation.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoCaracteristicaRequestVo {

    @UniqueValue(domainClass = ProdutoCaracteristica.class, fieldName = "ID")
    @NotNull(message = MessageConstants.CATEGORIA_INVALIDA)
    private Long caracteristicaId;

    @NotBlank(message = MessageConstants.DESCRICAO_CARACTERISTICA_INVALIDA)
    private String valor;

}
