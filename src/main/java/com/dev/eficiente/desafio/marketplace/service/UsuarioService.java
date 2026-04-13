package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.model.dto.UsuarioDTO;
import com.dev.eficiente.desafio.marketplace.model.entity.Usuario;
import com.dev.eficiente.desafio.marketplace.exception.BusinessException;
import com.dev.eficiente.desafio.marketplace.model.vo.UsuarioVo;
import com.dev.eficiente.desafio.marketplace.repository.UsuarioRepository;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = RuntimeException.class)
    public void createNovoUsuario(final UsuarioVo vo) throws BusinessException {

        if(Objects.isNull(vo.senha()) || Objects.isNull(vo.login())) {
            throw new BusinessException(MessageConstants.DADOS_INVALIDOS, HttpStatus.BAD_REQUEST);
        }

        String hashSenha = passwordEncoder.encode(vo.senha());
        Usuario novoUsuario = new Usuario(vo.login(), hashSenha, vo.dataCadastro());

        usuarioRepository.save(novoUsuario);

    }

    public UsuarioDTO getUsuarioLogado(final Principal usuarioLogado) {
        var usuario = usuarioRepository.findByLogin(usuarioLogado.getName().toString()).get();
        return new UsuarioDTO(usuario.getId(), usuario.getLogin());
    }

    public Usuario findUsuarioById(final Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", HttpStatus.NOT_FOUND));
    }
}
