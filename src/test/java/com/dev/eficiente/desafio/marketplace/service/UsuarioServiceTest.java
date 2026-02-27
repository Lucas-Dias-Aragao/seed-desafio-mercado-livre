package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.exception.BusinessException;
import com.dev.eficiente.desafio.marketplace.model.vo.UsuarioVo;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Não deve criar usuário se dados forem nulos")
    void naoDeveCriarUsuarioComDadosNulos() {

        UsuarioVo vo = new UsuarioVo(null, null, LocalDateTime.now().minusMinutes(1));
        BusinessException ex = assertThrows(BusinessException.class, () -> usuarioService.createNovoUsuario(vo));
        assertEquals(MessageConstants.DADOS_INVALIDOS, ex.getMensagem());
    }

    @Test
    @DisplayName("Não deve criar usuário se login for nulo")
    void naoDeveCriarUsuarioComLoginNulo() {

        UsuarioVo vo = new UsuarioVo(null, "123456", LocalDateTime.now().minusMinutes(1));
        BusinessException ex = assertThrows(BusinessException.class, () -> usuarioService.createNovoUsuario(vo));
        assertEquals(MessageConstants.DADOS_INVALIDOS, ex.getMensagem());
    }

}
