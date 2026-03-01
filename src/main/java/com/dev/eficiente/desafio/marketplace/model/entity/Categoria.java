package com.dev.eficiente.desafio.marketplace.model.entity;

import com.dev.eficiente.desafio.marketplace.model.vo.CategoriaRequestVo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "marketplace", name = "TB_CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "NOME", nullable = false)
    private String nome;

    @Setter
    @Getter
    @Column(name = "ID_CATEGORIA_MAE")
    private Long idCategoriaMae;

    @Deprecated
    public Categoria() {}

    public Categoria(final CategoriaRequestVo vo) {
        this.nome = vo.getNome();
        this.idCategoriaMae = vo.getIdCategoriaMae();
    }
}
