package com.dev.eficiente.desafio.marketplace.model.factory;

import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Pergunta;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.vo.PerguntaRequestVo;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoRequestVo;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class PerguntaFactory {

    public static Pergunta create(final PerguntaRequestVo vo, final Produto produto, final Long idUsuarioCadastro) {
        return Pergunta.builder()
                .pergunta(vo.pergunta())
                .instanteCriacao(LocalDateTime.now())
                .idUsuarioLog(idUsuarioCadastro)
                .produto(produto)
                .build();
    }

}
