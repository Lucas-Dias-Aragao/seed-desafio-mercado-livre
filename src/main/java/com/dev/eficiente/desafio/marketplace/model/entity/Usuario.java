package com.dev.eficiente.desafio.marketplace.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(schema = "marketplace", name = "TB_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN", nullable = false)
    private String login;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    private LocalDateTime dataInclusao;

    @Deprecated
    public Usuario(){}

    public Usuario(final String login, final String senha, final LocalDateTime dataInclusao) {
        this.login = login;
        this.senha = senha;
        this.dataInclusao = dataInclusao;
    }

}
