package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.exception.CaracteristicaCategoriaNotFoundException;
import com.dev.eficiente.desafio.marketplace.model.entity.CaracteristicaCategoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.enumeration.TipoCaracteristicaEnum;
import com.dev.eficiente.desafio.marketplace.repository.CaracteristicaCategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CaracteristicaCategoriaServiceTest {

    @InjectMocks
    private CaracteristicaCategoriaService categoriaService;

    @Mock
    private CaracteristicaCategoriaRepository caracteristicaCategoriaRepository;

    @Test
    @DisplayName("Deve retornar uma categoria existente por id")
    void deveRetornarCategoriaExistenteAoBuscarPorId() {
        Categoria categoria = new Categoria("Categoria Teste");
        CaracteristicaCategoria caracteristicaCategoria = new CaracteristicaCategoria("Teste", TipoCaracteristicaEnum.NUMERO, categoria);

        when(caracteristicaCategoriaRepository.findById(1L)).thenReturn(Optional.of(caracteristicaCategoria));

        var response = categoriaService.findById(1L);
        assertTrue(response.getNome().equals("Teste"));

    }

    @Test
    @DisplayName("Deve lançar exception se caracteristica categoria não existir")
    void deveLancarExceptionSeCaracteristicaCategoriaNaoExistir() {
        when(caracteristicaCategoriaRepository.findById(1L)).thenReturn(Optional.empty());

         var ex = assertThrows(CaracteristicaCategoriaNotFoundException.class, () -> {
             categoriaService.findById(1L);
         });

         assertEquals("Caracteristica não encontrada", ex.getMensagem());
    }

}
