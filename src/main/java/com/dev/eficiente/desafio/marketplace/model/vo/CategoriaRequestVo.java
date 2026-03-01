package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import com.dev.eficiente.desafio.marketplace.validation.annotation.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequestVo {

    @NotBlank(message = MessageConstants.NOME_CATEGORIA_INVALIDO)
    @UniqueValue(fieldName = "nome", domainClass = Categoria.class)
    private String nome;

    private Long idCategoriaMae;

}
