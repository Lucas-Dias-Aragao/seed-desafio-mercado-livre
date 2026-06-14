package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.dto.ResponseMessageDto;
import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.entity.Pergunta;
import com.dev.eficiente.desafio.marketplace.model.entity.Produto;
import com.dev.eficiente.desafio.marketplace.model.factory.PerguntaFactory;
import com.dev.eficiente.desafio.marketplace.model.vo.PerguntaRequestVo;
import com.dev.eficiente.desafio.marketplace.repository.PerguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerguntaService {

    private final PerguntaRepository perguntaRepository;
    private final ProdutoService produtoService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    public ResponseMessageDto cadastraNovaPergunta(final PerguntaRequestVo vo, final Long idProduto, final UsuarioDTO usuarioLogado) {
        Produto produto = produtoService.findProdutoById(idProduto);
        Pergunta pergunta = PerguntaFactory.create(vo, produto, usuarioLogado.getId());
        perguntaRepository.save(pergunta);
        prepararEnvioEmail(vo, produto, usuarioLogado);

        return new ResponseMessageDto("Pergunta enviada com sucesso!");
    }

    private void prepararEnvioEmail(final PerguntaRequestVo vo, final Produto produto, final UsuarioDTO usuarioLogado) {

        UsuarioDTO responsavelProduto = usuarioService.findUsuarioById(produto.getUsuarioLog());
        String linkProduto = "http://localhost:3011/produtos/" + produto.getId();

        String bodyEmail = """
                Você recebeu uma pergunta sobre o produto %s.
                Link produto: %s
                """.formatted(produto.getNome(), linkProduto);

        String assuntoEmail = "Você recebeu uma pergunta sobre " + produto.getNome();

        emailService.enviarEmail(responsavelProduto.getLogin(), assuntoEmail, bodyEmail);

    }

}
