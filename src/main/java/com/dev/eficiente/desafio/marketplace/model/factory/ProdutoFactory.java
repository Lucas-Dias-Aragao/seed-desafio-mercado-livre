package com.dev.eficiente.desafio.marketplace.model.factory;

import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoRequestVo;

import java.time.LocalDateTime;

public class ProdutoFactory {

    public static Produto create(final ProdutoRequestVo vo, final Categoria categoria) {
        return Produto.builder()
                .nome(vo.nome())
                .valor(vo.valor())
                .descricao(vo.descricao())
                .qtdDisponivel(vo.quantidade())
                .dataInclusao(LocalDateTime.now())
                .categoria(categoria)
                .build();
    }

}
