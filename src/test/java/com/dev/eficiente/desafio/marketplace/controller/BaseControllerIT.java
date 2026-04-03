package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.config.security.TokenService;
import com.dev.eficiente.desafio.marketplace.model.entity.CaracteristicaCategoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Categoria;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.entity.Usuario;
import com.dev.eficiente.desafio.marketplace.model.enumeration.TipoCaracteristicaEnum;
import com.dev.eficiente.desafio.marketplace.repository.CaracteristicaCategoriaRepository;
import com.dev.eficiente.desafio.marketplace.repository.CategoriaRepository;
import com.dev.eficiente.desafio.marketplace.repository.ProdutoRepository;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseControllerIT {

    public static Usuario usuario;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected CategoriaRepository categoriaRepository;

    @Autowired
    protected CaracteristicaCategoriaRepository caracteristicaCategoriaRepository;

    @Autowired
    protected ProdutoRepository produtoRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    protected static Categoria categoriaCelulares;
    protected static CaracteristicaCategoria caracteristicaCategoria1;
    protected static CaracteristicaCategoria caracteristicaCategoria2;
    protected static CaracteristicaCategoria caracteristicaCategoria3;

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

    void cargaInicio() {
        categoriaCelulares = createCategoria("Celulares");
        caracteristicaCategoria1 = createCaracteristicaCategoria("Memória RAM", TipoCaracteristicaEnum.NUMERO ,categoriaCelulares);
        caracteristicaCategoria2 = createCaracteristicaCategoria("Armazenamento", TipoCaracteristicaEnum.NUMERO ,categoriaCelulares);
        caracteristicaCategoria3 = createCaracteristicaCategoria("Dual Chip", TipoCaracteristicaEnum.BOOLEAN ,categoriaCelulares);
    }

    private CaracteristicaCategoria createCaracteristicaCategoria(final String nome, final TipoCaracteristicaEnum tipo, final Categoria categoria) {
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

    protected Produto createProduto(final Long idUsuarioCadastro) {
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

    @BeforeEach
    void beforeEach() {
        usuarioRepository.deleteAll();
        usuario = createUsuario();
    }
}
