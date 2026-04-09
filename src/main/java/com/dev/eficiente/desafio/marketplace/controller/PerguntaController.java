package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.vo.PerguntaRequestVo;
import com.dev.eficiente.desafio.marketplace.service.PerguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perguntas")
public class PerguntaController extends BaseController {

    private final PerguntaService perguntaService;

    @PostMapping("/{idProduto}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessageDto cadastraNovaPergunta(@RequestBody @Valid PerguntaRequestVo vo, @PathVariable("idProduto") Long idProduto, Principal principal) {
        return perguntaService.cadastraNovaPergunta(vo, idProduto, getUsuarioLogado(principal));
    }

}
