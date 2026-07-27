package com.labs.vgx.books.exceptions;

public class UsuarioCadastradoException extends RuntimeException{

    public UsuarioCadastradoException(){
        super();
    }
    public UsuarioCadastradoException(String message) {
        super(message);
    }
}
