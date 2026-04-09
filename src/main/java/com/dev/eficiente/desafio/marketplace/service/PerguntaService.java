package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.entity.Pergunta;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.factory.PerguntaFactory;
import com.dev.eficiente.desafio.marketplace.model.vo.PerguntaRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.PerguntaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final ProdutoService produtoService;

    public ResponseMessageDto cadastraNovaPergunta(@Valid PerguntaRequestVo vo, Long idProduto, UsuarioDTO usuarioLogado) {
        Produto produto = produtoService.findProdutoById(idProduto);
        //TODO: enviar e-mail para o vendedor
        Pergunta pergunta = PerguntaFactory.create(vo, produto, usuarioLogado.getId());
        perguntaRepository.save(pergunta);
        return new ResponseMessageDto("Pergunta enviada com sucesso!");
    }

}
