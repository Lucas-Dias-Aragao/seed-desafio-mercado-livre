package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.vo.PerguntaRequestVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerguntaService {

    private final ProdutoService produtoService;

    public ResponseMessageDto cadastraNovaPergunta(@Valid PerguntaRequestVo vo, Long idProduto, UsuarioDTO usuarioLogado) {
        Produto produto = produtoService.findProdutoById(idProduto);
        return new ResponseMessageDto("Pergunta enviada com sucesso!");
    }

}
