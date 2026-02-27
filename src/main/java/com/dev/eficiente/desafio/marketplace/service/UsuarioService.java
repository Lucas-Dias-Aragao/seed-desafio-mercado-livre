package com.dev.eficiente.desafio.marketplace.service;

import com.dev.eficiente.desafio.marketplace.entity.Usuario;
import com.dev.eficiente.desafio.marketplace.exception.BusinessException;
import com.dev.eficiente.desafio.marketplace.model.vo.UsuarioVo;
import com.dev.eficiente.desafio.marketplace.repository.UsuarioRepository;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = RuntimeException.class)
    public void createNovoUsuario(final UsuarioVo vo) throws BusinessException {

        if(Objects.isNull(vo.getSenha()) || Objects.isNull(vo.getLogin())) {
            throw new BusinessException(MessageConstants.DADOS_INVALIDOS, HttpStatus.BAD_REQUEST);
        }

        String hashSenha = passwordEncoder.encode(vo.getSenha());
        Usuario novoUsuario = new Usuario(vo.getLogin(), hashSenha, vo.getDataCadastro());

        usuarioRepository.save(novoUsuario);

    }
}
