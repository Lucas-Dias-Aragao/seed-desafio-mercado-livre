package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.model.entity.CaracteristicaCategoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.enumeration.TipoCaracteristicaEnum;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoCaracteristicaRequestVo;
import com.dev.eficiente.desafio.marketplace.model.vo.ProdutoRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.CaracteristicaCategoriaRepository;
import com.dev.eficiente.desafio.marketplace.repository.CategoriaRepository;
import com.dev.eficiente.desafio.marketplace.repository.ProdutoRepository;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ProdutoControllerIT extends BaseControllerIT {

    private static Categoria categoriaCelulares;
    private static CaracteristicaCategoria caracteristicaCategoria1;
    private static CaracteristicaCategoria caracteristicaCategoria2;
    private static CaracteristicaCategoria caracteristicaCategoria3;
    private static final String URL_PRODUTOS = "/produtos";
    private static final String URL_IMAGEM = "/imagens";
    private static final String URL_BARRA = "/";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CaracteristicaCategoriaRepository caracteristicaCategoriaRepository;

    @Nested
    @DisplayName("POST /produtos - 200 OK")
    class Success {

        @Test
        @DisplayName("Se dados válidos, cria produto com sucesso")
        void deveCriarProdutoComSucesso() throws Exception {
            List<ProdutoCaracteristicaRequestVo> caracteristicas = new ArrayList<>();
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria1.getId(), "8"));
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria2.getId(), "256"));
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria3.getId(), "true"));

            ProdutoRequestVo vo = new ProdutoRequestVo("Samsung Galaxy", "Smartphone Samsung Galaxy S23",
                    BigDecimal.valueOf(4299.99),10, categoriaCelulares.getId(), caracteristicas);

            MvcResult result = mockMvc.perform(post(URL_PRODUTOS)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();

            assertEquals(200, response.getStatus());
        }

    }

    @Nested
    @DisplayName("POST /produtos - 400 BAD REQUEST")
    class BadRequest {

        @Test
        @DisplayName("Não deve criar se dados informados forem inválidos")
        void naoDeveCriarProdutoComInformacoesInvalidas() throws Exception {
            List<ProdutoCaracteristicaRequestVo> caracteristicas = new ArrayList<>();
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria1.getId(), "8"));
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria2.getId(), "256"));
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria3.getId(), "true"));

            ProdutoRequestVo vo = new ProdutoRequestVo(null, null,
                    BigDecimal.valueOf(0),-1, null, caracteristicas);

            MvcResult result = mockMvc.perform(post(URL_PRODUTOS)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.VALOR_INVALIDO, errorResponse.get("valor"));
            assertEquals(MessageConstants.NOME_PRODUTO_INVALIDO, errorResponse.get("nome"));
            assertEquals(MessageConstants.DESCRICAO_INVALIDA, errorResponse.get("descricao"));
            assertEquals(MessageConstants.CATEGORIA_INVALIDA, errorResponse.get("categoriaId"));
            assertEquals(MessageConstants.QUANTIDADE_INVALIDA, errorResponse.get("quantidade"));
        }

        @Test
        @DisplayName("Não deve criar produto com menos de 3 características")
        void naoDeveCriarProdutoComMenosDe3Caracteristicas() throws Exception {
            List<ProdutoCaracteristicaRequestVo> caracteristicas = new ArrayList<>();
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria1.getId(), "8"));
            caracteristicas.add(new ProdutoCaracteristicaRequestVo(caracteristicaCategoria2.getId(), "256"));

            ProdutoRequestVo vo = new ProdutoRequestVo("Samsung Galaxy", "Smartphone Samsung Galaxy S23",
                    BigDecimal.valueOf(4299.99),10, categoriaCelulares.getId(), caracteristicas);

            MvcResult result = mockMvc.perform(post(URL_PRODUTOS)
                            .header(HttpHeaders.AUTHORIZATION, generatedToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(vo)))
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

            assertEquals(400, response.getStatus());
            assertEquals(MessageConstants.QTD_CARACTERISTICAS_INVALIDA, errorResponse.get("caracteristicas"));
        }
    }

    @Nested
    @DisplayName("POST /produtos/{id}/imagens")
    class PostImagemProduto {

        @Nested
        class Success {

            @Test
            @DisplayName("Se dados válidos, salve imagens do produto com sucesso")
            void deveFazerUploadDasImagensDoProdutoComSucesso() throws Exception {
                Produto produto = createProduto(usuario.getId());

                MockMultipartFile imagem = createMockImagem("img1", "conteudo fake");
                MockMultipartFile imagem2 = createMockImagem("img2", "conteudo fake");

                StringBuilder url = new StringBuilder(URL_PRODUTOS)
                        .append(URL_BARRA)
                        .append(produto.getId())
                        .append(URL_BARRA)
                        .append(URL_IMAGEM);

                MvcResult result = mockMvc.perform(multipart(url.toString())
                                .file(imagem)
                                .file(imagem2)
                                .header(HttpHeaders.AUTHORIZATION, generatedToken())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("POST");
                                    return request;
                                })
                        )
                        .andReturn();

                MockHttpServletResponse response = result.getResponse();

                assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class BadRequest {

            @Test
            @DisplayName("Se lista de imagens for vazia, deve lançar exception")
            void seListaDeImagensForVaziaDeveLancarException() throws Exception {
                Produto produto = createProduto(usuario.getId());

                StringBuilder url = new StringBuilder(URL_PRODUTOS)
                        .append(URL_BARRA)
                        .append(produto.getId())
                        .append(URL_BARRA)
                        .append(URL_IMAGEM);

                MvcResult result = mockMvc.perform(multipart(url.toString())
                                .header(HttpHeaders.AUTHORIZATION, generatedToken())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("POST");
                                    return request;
                                })
                        )
                        .andReturn();

                MockHttpServletResponse response = result.getResponse();
                Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

                assertEquals(400, response.getStatus());
                assertEquals(MessageConstants.QTD_IMAGENS_INVALIDA, errorResponse.get("imagens"));
            }

            @Test
            @DisplayName("deve lançar exception se conteudo da imagem for inválido")
            void deveLancarExceptionSeConteudoDeImagensForInvalido() throws Exception {
                Produto produto = createProduto(usuario.getId());
                MockMultipartFile imagem = createMockImagem("img1", "");

                StringBuilder url = new StringBuilder(URL_PRODUTOS)
                        .append(URL_BARRA)
                        .append(produto.getId())
                        .append(URL_BARRA)
                        .append(URL_IMAGEM);

                MvcResult result = mockMvc.perform(multipart(url.toString())
                                .file(imagem)
                                .header(HttpHeaders.AUTHORIZATION, generatedToken())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("POST");
                                    return request;
                                })
                        )
                        .andReturn();

                MockHttpServletResponse response = result.getResponse();
                Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

                assertEquals(400, response.getStatus());
                assertEquals("Imagem " + imagem.getOriginalFilename() + " não possui conteúdo válido", errorResponse.get("mensagem"));
            }

            @Test
            @DisplayName("Não deve permitir upload de imagem se o usuário não for o mesmo que criou o produto")
            void naoDevePermitirUploadDeImagensSeUsuarioForDiferenteDeQuemCriouOProduto() throws Exception {
                Produto produto = createProduto(58000L);

                MockMultipartFile imagem = createMockImagem("img1", "conteudo fake");
                MockMultipartFile imagem2 = createMockImagem("img2", "conteudo fake");

                StringBuilder url = new StringBuilder(URL_PRODUTOS)
                        .append(URL_BARRA)
                        .append(produto.getId())
                        .append(URL_BARRA)
                        .append(URL_IMAGEM);

                MvcResult result = mockMvc.perform(multipart(url.toString())
                                .file(imagem)
                                .file(imagem2)
                                .header(HttpHeaders.AUTHORIZATION, generatedToken())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("POST");
                                    return request;
                                })
                        )
                        .andReturn();

                MockHttpServletResponse response = result.getResponse();
                Map<String, String> errorResponse = objectMapper.readValue(response.getContentAsString(), Map.class);

                assertEquals(400, response.getStatus());
                assertEquals(MessageConstants.ACAO_NAO_PERMITIDA, errorResponse.get("mensagem"));
            }

        }

    }

    @BeforeEach
    void before() {
        produtoRepository.deleteAll();
        caracteristicaCategoriaRepository.deleteAll();
        categoriaRepository.deleteAll();
        cargaInicio();
    }

    void cargaInicio() {
        categoriaCelulares = createCategoria("Celulares");
        caracteristicaCategoria1 = createCaracteristicaCategoria("Memória RAM", TipoCaracteristicaEnum.NUMERO ,categoriaCelulares);
        caracteristicaCategoria2 = createCaracteristicaCategoria("Armazenamento", TipoCaracteristicaEnum.NUMERO ,categoriaCelulares);
        caracteristicaCategoria3 = createCaracteristicaCategoria("Dual Chip", TipoCaracteristicaEnum.BOOLEAN ,categoriaCelulares);
    }

    private CaracteristicaCategoria createCaracteristicaCategoria(final String nome, final TipoCaracteristicaEnum tipo,final Categoria categoria) {
        CaracteristicaCategoria novaCaracteristicaCategoria = new CaracteristicaCategoria(nome, tipo, categoria);
        novaCaracteristicaCategoria = caracteristicaCategoriaRepository.save(novaCaracteristicaCategoria);

        assertNotNull(novaCaracteristicaCategoria.getId());
        return novaCaracteristicaCategoria;
    }

    private Categoria createCategoria(final String nomeCategoria) {
        Categoria novaCategoria = new Categoria(nomeCategoria);
        novaCategoria = categoriaRepository.save(novaCategoria);

        assertNotNull(novaCategoria.getId());
        return novaCategoria;
    }

    private Produto createProduto(final Long idUsuarioCadastro) {
        Produto produto = Produto.builder()
                .nome("Smartphone Xing Ling")
                .descricao("Smartphone Xing Ling 256gb")
                .valor(BigDecimal.valueOf(1500))
                .qtdDisponivel(1)
                .dataInclusao(LocalDateTime.now())
                .categoria(categoriaCelulares)
                .caracteristicas(List.of())
                .usuarioLog(idUsuarioCadastro)
                .build();

        produto = produtoRepository.save(produto);
        assertNotNull(produto.getId());

        return produto;
    }

    private MockMultipartFile createMockImagem(final String nomeImagem, final String conteudo) {
        return new MockMultipartFile(
                "imagens",
                nomeImagem + ".jpg",
                MediaType.IMAGE_JPEG_VALUE,
                conteudo.getBytes()
        );
    }
}
