package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.vo.NovasImagensRequest;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoRequestVo;
import com.dev.eficiente.desafio.marketplace.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController extends BaseController {

    private final ProdutoService produtoService;

    @PostMapping
    public void cadastraProduto(@RequestBody @Valid ProdutoRequestVo vo) {
        produtoService.cadastraProduto(vo);
    }

    @PostMapping("/{idProduto}/imagens")
    public void cadastraImagensProduto(@PathVariable("idProduto") Long idProduto, @Valid NovasImagensRequest imagens,
                                        final Principal usuarioLogado) {
        produtoService.cadastraImagensProduto(idProduto, imagens, getUsuarioLogado(usuarioLogado));
    }
}
