package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.vo.PerguntaRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.PerguntaRepository;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class PerguntaControllerIT extends BaseControllerIT {

    private final static String URL_PERGUNTAS = "/perguntas";

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Nested
    @DisplayName("POST /perguntas - 200 OK")
    class Success {

        @Test
        @DisplayName("Se dados válidos, deve cadastrar pergunta com sucesso")
        void testPostPerguntasComSucesso() throws Exception {

            Produto produto = createProduto(usuario.getId());

            PerguntaRequestVo vo = new PerguntaRequestVo("Ainda tem em estoque?");

            String url = URL_PERGUNTAS + "/" + produto.getId();

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();

            assertEquals(200, response.getStatus());

            var pergunta = perguntaRepository.findAll();
            assertEquals(1, pergunta.size());
            assertTrue(vo.pergunta().equals(pergunta.getFirst().getPergunta()));

        }

    }

    @Nested
    @DisplayName("POST /perguntas - 4XX")
    class Fail {

        @Test
        @DisplayName("Se conteúdo da pergunta for nulo, deve lançar exception")
        void testDeveLancarExceptionSeConteudoPerguntaForNulo() throws Exception {
            PerguntaRequestVo vo = new PerguntaRequestVo(null);

            String url = URL_PERGUNTAS + "/" + 5555;

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.TAMANHO_PERGUNTA_INVALIDA, errorResponse.get("pergunta"));

        }

        @Test
        @DisplayName("Se conteúdo da pergunta for blank, deve lançar exception")
        void testDeveLancarExceptionSeConteudoPerguntaForBlank() throws Exception {
            PerguntaRequestVo vo = new PerguntaRequestVo("");

            String url = URL_PERGUNTAS + "/" + 5555;

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.TAMANHO_PERGUNTA_INVALIDA, errorResponse.get("pergunta"));

        }

        @Test
        @DisplayName("Se produto não existir, deve lançar exception")
        void testDeveLancarExceptionSeProdutoNaoExistir() throws Exception {
            PerguntaRequestVo vo = new PerguntaRequestVo("Ainda tem em estoque?");

            String url = URL_PERGUNTAS + "/" + 55555;

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(404, response.getStatus());
            assertEquals("Produto não encontrado", errorResponse.get("mensagem"));

        }

    }

    @BeforeAll
    void setUpPerguntas() {
        cargaInicio();
    }

    @BeforeEach
    void beforeEachPerguntas() {
        perguntaRepository.deleteAllInBatch();
    }

}
