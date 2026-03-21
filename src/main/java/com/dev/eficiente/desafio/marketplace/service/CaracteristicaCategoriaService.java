package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.exception.CaracteristicaCategoriaNotFoundException;
import com.dev.eficiente.desafio.marketplace.model.entity.CaracteristicaCategoria;
import com.dev.eficiente.desafio.marketplace.repository.CaracteristicaCategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaracteristicaCategoriaService {

    private final CaracteristicaCategoriaRepository caracteristicaCategoriaRepository;

    public CaracteristicaCategoria findById(final Long id) {
        return caracteristicaCategoriaRepository.findById(id).orElseThrow(CaracteristicaCategoriaNotFoundException::new);
    }
}
