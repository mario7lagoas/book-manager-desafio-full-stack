package com.labs.vgx.books.enums;

import lombok.Getter;

@Getter
public enum ErrosEnum {
    NAO_ENCONTRADO("Não encontrado."),
    USUARIO_NAO_ENCONTRADO("Usuario não encontrado."),
    USUARIO_JA_CADASTRADO("Usuario já Cadastrado."),
    NAO_PERMITIDO("Não permitido."),
    ENDPOINT_NAO_ENCONTRADO("Endpoint não encontrado."),
    PAYLOAD_INVALIDO("Payload Invalido."),
    CAMPO_OBRIGATORIO("Campo obrigatorio."),
    LIVRO_JA_CADASTRADO("Livro cadastrado."),
    METODO_NAO_SUPORTADO("Método não suportado.");

    private String nome;

    ErrosEnum(String nome){
        this.nome = nome;
    }

    public String toString(){
        return nome;
    }
}
