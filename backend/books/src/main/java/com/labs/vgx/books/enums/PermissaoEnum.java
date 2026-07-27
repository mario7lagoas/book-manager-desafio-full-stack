package com.labs.vgx.books.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissaoEnum {
    BUSCAR_BOOK(1, "Buscar Livros", "ROLE_BUSCAR_BOOK"),
    ALTERAR_BOOK(2, "Editar Livros", "ROLE_ALTERAR_BOOK"),
    CADASTRAR_BOOK(3, "Cadastrar Livros", "ROLE_CADASTRAR_BOOK"),
    APAGAR_BOOK(4, "Apagar Livros", "ROLE_APAGAR_BOOK");

    private int codigo;
    private String nome;
    private String role;

    public static PermissaoEnum toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (PermissaoEnum x : PermissaoEnum.values()){
            if (codigo.equals(x.getCodigo())){
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + codigo);
    }
}
