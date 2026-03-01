package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.vo.CategoriaRequestVo;
import com.dev.eficiente.desafio.marketplace.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public String criaNovaCategoria(@RequestBody @Valid CategoriaRequestVo vo) {
        String response = categoriaService.createCategoria(vo);
        return "Categoria " + response + " criada com sucesso.";
    }

}
