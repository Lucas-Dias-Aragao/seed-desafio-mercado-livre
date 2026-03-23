package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.config.security.TokenService;
import com.dev.eficiente.desafio.marketplace.model.entity.Usuario;
import com.dev.eficiente.desafio.marketplace.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseControllerIT {

    public static Usuario usuario;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        usuario = createUsuario();
    }

    Usuario createUsuario() {
        Usuario novoUsuario = new Usuario("usuarioteste@email.com", "1234567",
                LocalDateTime.now().minusMinutes(5));
        novoUsuario = usuarioRepository.saveAndFlush(novoUsuario);
        assertNotNull(novoUsuario.getId());
        return novoUsuario;
    }

    protected String generatedToken() {
        UserDetails user = User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .authorities(Collections.emptyList())
                .build();

        return "Bearer " + tokenService.gerarToken(user);
    }

    @BeforeEach
    void beforeEach() {
        usuarioRepository.deleteAll();
        usuario = createUsuario();
    }
}
