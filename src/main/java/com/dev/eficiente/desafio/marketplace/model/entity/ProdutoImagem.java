package com.dev.eficiente.desafio.marketplace.model.entity;

import com.dev.eficiente.desafio.marketplace.model.enumeration.StatusEnum;
import com.dev.eficiente.desafio.marketplace.model.enumeration.converter.StatusEnumConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(schema = "marketplace", name = "TB_PRODUTO_IMAGEM")
public class ProdutoImagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    @NotNull
    private String urlProduto;

    @NotNull
    private Long usuarioLog;

    @NotNull
    @Convert(converter = StatusEnumConverter.class)
    private StatusEnum status;

    public ProdutoImagem(final Produto produto, final String urlProduto, final Long usuario) {
        this.produto = produto;
        this.urlProduto = urlProduto;
        this.usuarioLog = usuario;
        this.status = StatusEnum.ATIVO;
    }

}
