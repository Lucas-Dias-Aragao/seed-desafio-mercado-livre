package com.dev.eficiente.desafio.marketplace.model.factory;

import com.dev.eficiente.desafio.marketplace.model.entity.Avaliacao;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.vo.AvaliacaoRequestVo;

public class AvaliacaoFactory {

    public static Avaliacao create(final AvaliacaoRequestVo vo, final Produto produto, final Long idUsuarioCadastro) {

        return Avaliacao.builder()
                .estrela(vo.estrela())
                .titulo(vo.titulo())
                .descricao(vo.descricao())
                .idUsuarioLog(idUsuarioCadastro)
                .produto(produto)
                .build();
    }

}
