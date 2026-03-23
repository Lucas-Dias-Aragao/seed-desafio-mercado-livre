package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRequestVo (

    @NotBlank(message = MessageConstants.NOME_PRODUTO_INVALIDO)
    String nome,

    @NotBlank(message = MessageConstants.DESCRICAO_INVALIDA)
    @Size(min = 10, max = 1000, message = MessageConstants.DESCRICAO_INVALIDA)
    String descricao,

    @NotNull(message = MessageConstants.VALOR_INVALIDO)
    @Min(value = 1, message = MessageConstants.VALOR_INVALIDO)
    BigDecimal valor,

    @NotNull(message = MessageConstants.QUANTIDADE_INVALIDA)
    @Min(value = 1, message = MessageConstants.QUANTIDADE_INVALIDA)
    Integer quantidade,

    @NotNull(message = MessageConstants.CATEGORIA_INVALIDA)
    Long categoriaId,

    @Size(min = 3, message = MessageConstants.QTD_CARACTERISTICAS_INVALIDA)
    List<ProdutoCaracteristicaRequestVo> caracteristicas
){}
