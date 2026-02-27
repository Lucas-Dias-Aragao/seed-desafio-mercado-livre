package com.dev.eficiente.desafio.marketplace.model.vo;

import com.dev.eficiente.desafio.marketplace.utils.MessageConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioVo {

    @NotBlank(message = MessageConstants.LOGIN_OBRIGATORIO)
    @Email(message = MessageConstants.FORMATO_LOGIN_INVALIDO)
    private String login;

    @NotBlank(message = MessageConstants.SENHA_OBRIGATORIA)
    @Size(min = 6, message = MessageConstants.TAMANHO_INVALIDO)
    private String senha;

    @NotNull(message = MessageConstants.DATA_OBRIGATORIA)
    @PastOrPresent(message = MessageConstants.CADASTRO_NAO_PODE_SER_NO_FUTURO)
    private LocalDateTime dataCadastro;

}
