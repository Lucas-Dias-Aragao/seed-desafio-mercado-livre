package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.vo.CategoriaRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Deve cadastrar categoria se dados forem válidos")
    void deveCriarCategoriaSeDadosForemValidos() {
        CategoriaRequestVo vo = CategoriaRequestVo.builder().nome("Teste").idCategoriaMae(2L).build();
        when(categoriaRepository.existsById(vo.idCategoriaMae())).thenReturn(true);

        Categoria novaCategoria = new Categoria(vo);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(novaCategoria);

        String response = categoriaService.createCategoria(vo);

        assertEquals("Teste", response);
        verify(categoriaRepository).existsById(2L);
        verify(categoriaRepository).save(any(Categoria.class));

    }

}
