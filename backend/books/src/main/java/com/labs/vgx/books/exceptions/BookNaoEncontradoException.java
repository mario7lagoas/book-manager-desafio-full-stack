package com.labs.vgx.books.exceptions;

public class BookNaoEncontradoException extends RuntimeException {
    public BookNaoEncontradoException() {
        super();
    }
    public BookNaoEncontradoException(String message) {
        super(message);
    }
}
