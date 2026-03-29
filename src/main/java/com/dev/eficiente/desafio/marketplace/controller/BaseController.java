package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

public class BaseController {

    @Autowired
    private UsuarioService usuarioService;

    protected UsuarioDTO getUsuarioLogado(final Principal usuario) {
        return usuarioService.getUsuarioLogado(usuario);
    }

}
