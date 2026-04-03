package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.vo.AvaliacaoRequestVo;
import com.dev.eficiente.desafio.marketplace.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avaliacao")
public class AvaliacaoController extends BaseController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessageDto cadastraAvaliacao(@RequestBody @Valid AvaliacaoRequestVo vo, Principal usuarioLogado) {
        return avaliacaoService.cadastraAvaliacao(vo, getUsuarioLogado(usuarioLogado));
    }

}
