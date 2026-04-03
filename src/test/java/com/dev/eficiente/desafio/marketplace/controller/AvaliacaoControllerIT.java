package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.vo.AvaliacaoRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.AvaliacaoRepository;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
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

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AvaliacaoControllerIT extends BaseControllerIT {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    private static final String URL_AVALIACAO = "/avaliacao";

    @Nested
    @DisplayName("POST /avaliacao - 200 OK ")
    class Success {

        @Test
        @DisplayName("Se dados válidos, cria avaliação com sucesso")
        void deveCriarAvaliacaoComSucesso() throws Exception {
            AvaliacaoRequestVo vo = AvaliacaoRequestVo.builder().estrela((short) 5)
                    .titulo("Avaliação teste").descricao("Teste de criação da avaliação").build();

            Produto produto = createProduto(usuario.getId());

            String url = URL_AVALIACAO + "/" + produto.getId();

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            ResponseMessageDto responseMessage = objectMapper.readValue(response.getContentAsString(), ResponseMessageDto.class);

            assertEquals(200, response.getStatus());
            assertEquals("Avaliação enviada com sucesso!", responseMessage.getMensagem());
        }

    }

    @Nested
    @DisplayName("POST /avaliacao - 400 Bad Request")
    class BadRequest {

        @Test
        @DisplayName("Se número de estrelas for maior que 5, título e descrição forem vazios, não deve criar avaliação")
        void naoDeveCriarAvaliacaoComDadosInvalidos() throws Exception {
            AvaliacaoRequestVo vo = AvaliacaoRequestVo.builder().estrela((short) 6)
                    .titulo("").descricao("").build();

            Produto produto = createProduto(usuario.getId());

            String url = URL_AVALIACAO + "/" + produto.getId();

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map responseMessage = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.TITULO_INVALIDO, responseMessage.get("titulo"));
            assertEquals(MessageConstants.QTD_ESTRELA_INVALIDA, responseMessage.get("estrela"));
            assertEquals(MessageConstants.DESCRICAO_AVALIACAO_INVALIDA, responseMessage.get("descricao"));

        }

        @Test
        @DisplayName("Se número de estrelas for menor que 1, não deve criar avaliação")
        void naoDeveCriarAvaliacaoSeQuantidadeEstrelasMenorQueUma() throws Exception {
            AvaliacaoRequestVo vo = AvaliacaoRequestVo.builder().estrela((short) 0)
                    .titulo("Teste").descricao("descrição da avaliação de teste").build();

            Produto produto = createProduto(usuario.getId());

            String url = URL_AVALIACAO + "/" + produto.getId();

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map responseMessage = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.QTD_ESTRELA_INVALIDA, responseMessage.get("estrela"));
        }

        @Test
        @DisplayName("Se descrição tiver mais que 500 caracteres não deve criar avaliação")
        void naoDeveCriarAvaliacaoSeDescricaoForMaiorQue500Caracteres() throws Exception {
            String descricao = """
                    Lorem Ipsum is simply dummy text of the printing and typesetting industry.
                    Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
                    when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                    It has survived not only five centuries, but also the leap into electronic
                     typesetting, remaining essentially unchanged.
                    It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
                     and more recently with desktop pu
                    """;

            assertTrue(
                    BigDecimal.valueOf(500).compareTo(
                    BigDecimal.valueOf(descricao.length())
                    ) < 0);


            AvaliacaoRequestVo vo = AvaliacaoRequestVo.builder().estrela((short) 0)
                    .titulo("Teste").descricao(descricao).build();

            Produto produto = createProduto(usuario.getId());

            String url = URL_AVALIACAO + "/" + produto.getId();

            MvcResult result = mockMvc.perform(post(url)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map responseMessage = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.DESCRICAO_AVALIACAO_INVALIDA, responseMessage.get("descricao"));
        }

    }

    @BeforeEach
    void before() {
        avaliacaoRepository.deleteAll();
        produtoRepository.deleteAll();
        caracteristicaCategoriaRepository.deleteAll();
        categoriaRepository.deleteAll();
        cargaInicio();
    }

}
