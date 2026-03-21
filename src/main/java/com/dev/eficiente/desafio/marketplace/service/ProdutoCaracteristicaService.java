package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.entity.ProdutoCaracteristica;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoCaracteristicaRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.ProdutoCaracteristicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoCaracteristicaService {

    private final ProdutoCaracteristicaRepository caracteristicasRepository;
    private final CaracteristicaCategoriaService caracteristicaCategoriaService;

    public List<ProdutoCaracteristica> createCaracteristicasProduto(final List<ProdutoCaracteristicaRequestVo> caracteristicas,
                                                                    final Produto produto) {

        List<ProdutoCaracteristica> caracteristicaList = caracteristicas.stream()
                .map(c ->
                        new ProdutoCaracteristica(
                        produto,
                        caracteristicaCategoriaService.findById(c.caracteristicaCategoriaId()),
                        c.valor()
                ))
                .collect(Collectors.toList());

        return caracteristicasRepository.saveAll(caracteristicaList);
    }

}
