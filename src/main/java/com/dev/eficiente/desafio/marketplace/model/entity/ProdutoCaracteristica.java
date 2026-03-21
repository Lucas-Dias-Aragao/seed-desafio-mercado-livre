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
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(schema = "marketplace", name = "TB_PRODUTO_CARACTERISTICA")
public class ProdutoCaracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CARACTERISTICA_CATEGORIA", nullable = false)
    private CaracteristicaCategoria caracteristica;

    @Column(name = "VALOR", nullable = false)
    private String valor;

    public ProdutoCaracteristica(final Produto produto, final CaracteristicaCategoria caracteristica, final String valor) {
        this.produto = produto;
        this.caracteristica = caracteristica;
        this.valor = valor;
    }

}
