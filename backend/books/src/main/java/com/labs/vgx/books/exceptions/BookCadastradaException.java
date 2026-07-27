package com.labs.vgx.books.exceptions;

public class BookCadastradaException extends RuntimeException{
    public BookCadastradaException() {
        super();
    }
    public BookCadastradaException(String message) {
        super(message);
    }
}
