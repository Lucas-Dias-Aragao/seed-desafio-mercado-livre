package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.entity.Avaliacao;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.factory.AvaliacaoFactory;
import com.dev.eficiente.desafio.marketplace.model.vo.AvaliacaoRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.AvaliacaoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final ProdutoService produtoService;

    @Transactional
    public ResponseMessageDto cadastraAvaliacao(final AvaliacaoRequestVo vo, Long idProduto, final UsuarioDTO usuarioLogado) {
        Produto produto = produtoService.findProdutoById(idProduto);
        Avaliacao avaliacao = AvaliacaoFactory.create(vo, produto, usuarioLogado.getId());
        avaliacaoRepository.save(avaliacao);

        return new ResponseMessageDto("Avaliação enviada com sucesso!");
    }
}
