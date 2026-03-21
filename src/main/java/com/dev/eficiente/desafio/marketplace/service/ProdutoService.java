package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.exception.CategoriaNotFoundException;
import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.entity.ProdutoCaracteristica;
import com.dev.eficiente.desafio.marketplace.model.factory.ProdutoFactory;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoCaracteristicaRequestVo;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.CategoriaRepository;
import com.dev.eficiente.desafio.marketplace.repository.ProdutoCaracteristicaRepository;
import com.dev.eficiente.desafio.marketplace.repository.ProdutoRepository;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final ProdutoCaracteristicaService caracteristicasService;

    @Transactional
    public void cadastraProduto(ProdutoRequestVo vo) {

        Categoria categoria = categoriaService.findCategoriaById(vo.categoriaId());
        Produto produto = ProdutoFactory.create(vo, categoria);

        produto = produtoRepository.saveAndFlush(produto);

        caracteristicasService.createCaracteristicasProduto(vo.caracteristicas(), produto);

    }
}
