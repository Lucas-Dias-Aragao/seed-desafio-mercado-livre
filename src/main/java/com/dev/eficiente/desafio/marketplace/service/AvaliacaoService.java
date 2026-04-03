package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.vo.AvaliacaoRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public ResponseMessageDto cadastraAvaliacao(final AvaliacaoRequestVo vo, final UsuarioDTO usuarioLogado) {



        return new ResponseMessageDto("Avaliação enviada com sucesso!");
    }
}
