package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.model.entity.Usuario;
import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import com.dev.eficiente.desafio.marketplace.validation.annotation.UniqueValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UsuarioVo (

        @NotBlank(message = MessageConstants.LOGIN_OBRIGATORIO)
        @Email(message = MessageConstants.FORMATO_LOGIN_INVALIDO)
        @UniqueValue(fieldName = "login", domainClass = Usuario.class, message = MessageConstants.EMAIL_JA_CADASTRADO)
        String login,

        @NotBlank(message = MessageConstants.SENHA_OBRIGATORIA)
        @Size(min = 6, message = MessageConstants.TAMANHO_INVALIDO)
        String senha,

        @NotNull(message = MessageConstants.DATA_OBRIGATORIA)
        @PastOrPresent(message = MessageConstants.CADASTRO_NAO_PODE_SER_NO_FUTURO)
        LocalDateTime dataCadastro
) {
}
