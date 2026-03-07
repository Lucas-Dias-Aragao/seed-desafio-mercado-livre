package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.exception.ErrorResponse;
import com.dev.eficiente.desafio.marketplace.model.entity.Usuario;
import com.dev.eficiente.desafio.marketplace.model.vo.UsuarioVo;
import com.dev.eficiente.desafio.marketplace.repository.UsuarioRepository;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UsuarioControllerIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String URL_USUARIO = "/usuarios";

    @Nested
    @DisplayName("POST /usuarios - 200 OK")
    class Success {

        @Test
        @DisplayName("Se dados válidos, cria usuário com sucesso")
        void deveCriarUsuarioComSucesso() throws Exception {
            UsuarioVo vo = new UsuarioVo("login@login.com", "1234567", LocalDateTime.now().minusMinutes(1));

            MvcResult result = mockMvc.perform(post(URL_USUARIO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();

            assertEquals(200, response.getStatus());
        }

    }

    @Nested
    @DisplayName("POST /usuarios - 400 Bad Request")
    class BadRequest {

        @Test
        @DisplayName("Se dados inválidos, não deve criar usuário")
        void naoDeveCriarUsuarioComDadosInvalidos() throws Exception {
            UsuarioVo vo = new UsuarioVo(null, null, null);

            MvcResult result = mockMvc.perform(post(URL_USUARIO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.LOGIN_OBRIGATORIO, errorResponse.get("login"));
            assertEquals(MessageConstants.SENHA_OBRIGATORIA, errorResponse.get("password"));
            assertEquals(MessageConstants.DATA_OBRIGATORIA, errorResponse.get("dataCadastro"));

        }

        @Test
        @DisplayName("Não deve criar usuários com o mesmo login")
        void naoDeveCriarUsuarioSeEmailJaForCadastrado() throws Exception {
            Usuario usuario = new Usuario("login@login.com", "123456", LocalDateTime.now().minusMinutes(1));
            usuarioRepository.saveAndFlush(usuario);

            UsuarioVo vo = new UsuarioVo("login@login.com", "123456", LocalDateTime.now().minusMinutes(1));

            MvcResult result = mockMvc.perform(post(URL_USUARIO)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.EMAIL_JA_CADASTRADO, errorResponse.get("login"));
        }
    }

    @BeforeEach
    void before() {
        usuarioRepository.deleteAll();
    }
}
