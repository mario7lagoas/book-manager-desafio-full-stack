package com.labs.vgx.books.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(){
        super();
    }

    public BadRequestException(String mensagem){
        super(mensagem);
    }

}
