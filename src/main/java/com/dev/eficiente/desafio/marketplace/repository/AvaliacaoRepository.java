package com.dev.eficiente.desafio.marketplace.repository;

import com.dev.eficiente.desafio.marketplace.model.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
