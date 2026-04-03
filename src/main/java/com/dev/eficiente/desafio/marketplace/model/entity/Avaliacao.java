package com.dev.eficiente.desafio.marketplace.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "marketplace", name = "TB_AVALIACAO")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ESTRELA", nullable = false)
    @Min(1)
    @Max(5)
    private Short estrela;

    @Column(name = "TITULO", nullable = false)
    private String titulo;

    @Column(name = "DESCRICAO")
    @Size(min = 10, max = 500)
    private String descricao;

    @Column(name = "ID_USUARIO_LOG")
    private Long idUsuarioLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;

}
