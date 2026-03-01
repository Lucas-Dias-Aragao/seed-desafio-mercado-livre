package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.vo.CategoriaRequestVo;
import com.dev.eficiente.desafio.marketplace.model.vo.UsuarioVo;
import com.dev.eficiente.desafio.marketplace.repository.CategoriaRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CategoriaControllerIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final String URL_CATEGORIA = "/categoria";

    @Nested
    @DisplayName("POST /categoria - 200 OK")
    class Success {

        @Test
        @DisplayName("Se dados válidos, cria categoria com sucesso")
        void deveCriarCategoriaComSucesso() throws Exception {
            CategoriaRequestVo vo = CategoriaRequestVo.builder().nome("Teste").build();

            MvcResult result = mockMvc.perform(post(URL_CATEGORIA)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();

            assertEquals(200, response.getStatus());
            assertEquals("Categoria " + vo.getNome() + " criada com sucesso.", response.getContentAsString());
        }

    }

    @Nested
    @DisplayName("POST /categoria - 200 OK")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar categoria com nome inválido")
        void naoDeveCriarCategoriaSeNomeForInvalido() throws Exception {
            CategoriaRequestVo vo = CategoriaRequestVo.builder().nome("").build();

            MvcResult result = mockMvc.perform(post(URL_CATEGORIA)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();

            assertEquals(400, response.getStatus());
            assertTrue(response.getContentAsString().contains(MessageConstants.NOME_CATEGORIA_INVALIDO));
        }

        @Test
        @DisplayName("Não deve criar categoria com id categoria mãe inválido")
        void naoDeveCriarCategoriaSeIdCategoriaMaeForInvalido() throws Exception {
            CategoriaRequestVo vo = CategoriaRequestVo.builder().nome("Teste").idCategoriaMae(1L).build();

            MvcResult result = mockMvc.perform(post(URL_CATEGORIA)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();

            assertEquals(400, response.getStatus());
            assertTrue(response.getContentAsString().contains(MessageConstants.CATEGORIA_MAE_INVALIDA));
        }

        @Test
        @DisplayName("Não deve criar categoria se já existir outra com o mesmo nome")
        void naoDeveCriarCategoriaJaExistirCategoriaComMesmoNome() throws Exception {
            Categoria categoria = new Categoria("Teste");
            categoriaRepository.save(categoria);

            CategoriaRequestVo vo = CategoriaRequestVo.builder().nome("Teste").idCategoriaMae(1L).build();

            MvcResult result = mockMvc.perform(post(URL_CATEGORIA)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();

            assertEquals(400, response.getStatus());
            assertTrue(response.getContentAsString().contains(MessageConstants.INFORMACAO_JA_CADASTRADA_ANTERIORMENTE));

        }

    }

    @BeforeEach
    void before() {
        categoriaRepository.deleteAll();
    }
}
