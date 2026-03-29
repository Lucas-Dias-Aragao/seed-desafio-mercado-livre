package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.entity.ProdutoImagem;
import com.dev.eficiente.desafio.marketplace.repository.ProdutoImagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoImagemService {

    private final ProdutoImagemRepository produtoImagemRepository;

    public void relacionaProdutoImagem(final Produto produto, final List<String> links, final UsuarioDTO usuarioLogado) {
        List<ProdutoImagem> imagens = new ArrayList<>();

        for(String link : links) {
            imagens.add(new ProdutoImagem(produto, link, usuarioLogado.getId()));
        }

        produtoImagemRepository.saveAll(imagens);
    }
}
