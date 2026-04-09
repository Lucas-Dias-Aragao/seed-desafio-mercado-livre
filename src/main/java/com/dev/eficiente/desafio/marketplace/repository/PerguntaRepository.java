package com.dev.eficiente.desafio.marketplace.repository;

import com.dev.eficiente.desafio.marketplace.model.entity.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
}
