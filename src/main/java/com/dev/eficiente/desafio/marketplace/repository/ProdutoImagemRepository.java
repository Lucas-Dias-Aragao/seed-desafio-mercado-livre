package com.dev.eficiente.desafio.marketplace.repository;

import com.dev.eficiente.desafio.marketplace.model.entity.ProdutoImagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoImagemRepository extends JpaRepository<ProdutoImagem, Long> {
}
