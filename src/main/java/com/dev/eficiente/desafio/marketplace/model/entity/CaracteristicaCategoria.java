package com.dev.eficiente.desafio.marketplace.model.entity;

import com.dev.eficiente.desafio.marketplace.model.enumeration.TipoCaracteristicaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(schema = "marketplace", name = "TB_CARACTERISTICA_CATEGORIA")
public class CaracteristicaCategoria {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "NOME", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CARACTERISTICA", nullable = false)
    private TipoCaracteristicaEnum tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    public CaracteristicaCategoria(final String nome, final TipoCaracteristicaEnum tipo, final Categoria categoria) {
        this.nome = nome;
        this.tipo = tipo;
        this.categoria = categoria;
    }

}
