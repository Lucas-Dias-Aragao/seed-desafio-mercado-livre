package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.exception.BusinessException;
import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.factory.ProdutoFactory;
import com.dev.eficiente.desafio.marketplace.model.vo.NovasImagensRequest;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final ProdutoCaracteristicaService caracteristicasService;
    private final UploaderFake uploaderFake;
    private final ProdutoImagemService produtoImagemService;

    @Transactional
    public void cadastraProduto(ProdutoRequestVo vo) {

        Categoria categoria = categoriaService.findCategoriaById(vo.categoriaId());
        Produto produto = ProdutoFactory.create(vo, categoria);

        produto = produtoRepository.saveAndFlush(produto);

        caracteristicasService.createCaracteristicasProduto(vo.caracteristicas(), produto);

    }

    public void cadastraImagensProduto(final Long idProduto, final NovasImagensRequest imagens, final UsuarioDTO usuarioLogado) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new BusinessException("Produto não encontrado", HttpStatus.NOT_FOUND));

        List<String> links = uploaderFake.upload(imagens.getImagens());
        produtoImagemService.relacionaProdutoImagem(produto, links, usuarioLogado);
    }
}
