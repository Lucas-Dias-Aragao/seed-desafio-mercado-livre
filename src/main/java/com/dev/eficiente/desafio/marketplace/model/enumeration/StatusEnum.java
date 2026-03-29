package com.dev.eficiente.desafio.marketplace.model.enumeration;

public enum StatusEnum {

    ATIVO((short) 1, "Ativo"),
    EXCLUIDO((short) 2, "Excluído");

    private Short id;
    private String descricao;

    StatusEnum(short id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Short getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
