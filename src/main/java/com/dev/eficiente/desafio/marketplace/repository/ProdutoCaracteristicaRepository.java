package com.dev.eficiente.desafio.marketplace.repository;

import com.dev.eficiente.desafio.marketplace.model.entity.ProdutoCaracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoCaracteristicaRepository extends JpaRepository<ProdutoCaracteristica, Long> {
}
