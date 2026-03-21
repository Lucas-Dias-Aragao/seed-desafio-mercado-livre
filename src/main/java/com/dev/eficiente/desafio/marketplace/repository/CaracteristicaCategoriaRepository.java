package com.dev.eficiente.desafio.marketplace.repository;

import com.dev.eficiente.desafio.marketplace.model.entity.CaracteristicaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaCategoriaRepository extends JpaRepository<CaracteristicaCategoria, Long> {
}
