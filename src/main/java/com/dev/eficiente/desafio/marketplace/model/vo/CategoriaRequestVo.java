package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import com.dev.eficiente.desafio.marketplace.validation.annotation.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CategoriaRequestVo (

    @NotBlank(message = MessageConstants.NOME_CATEGORIA_INVALIDO)
    @UniqueValue(fieldName = "nome", domainClass = Categoria.class)
    String nome,

    Long idCategoriaMae
){}
