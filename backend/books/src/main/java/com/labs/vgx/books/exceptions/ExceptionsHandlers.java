package com.labs.vgx.books.exceptions;

import com.labs.vgx.books.builders.ErroResponseBuilder;
import com.labs.vgx.books.builders.ErrorApiResponseBuilder;
import com.labs.vgx.books.enums.ErrosEnum;
import com.labs.vgx.books.models.ErrorApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;

@ControllerAdvice
public class ExceptionsHandlers {


    @ExceptionHandler(BookNaoEncontradoException.class)
    public ResponseEntity<ErrorApiResponse> bookNaoEncontradoExceptionHandler(BookNaoEncontradoException ex) {

        return new ResponseEntity<>(getNotfound(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookCadastradaException.class)
    public ResponseEntity<ErrorApiResponse> bookCadastradaExceptionHandler(BookCadastradaException ex) {

        return new ResponseEntity<>(getUnProcessable(ex, ErrosEnum.LIVRO_JA_CADASTRADO.toString()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UsuarioCadastradoException.class)
    public ResponseEntity<ErrorApiResponse> UsuarioCadastradoExceptionHandler(UsuarioCadastradoException ex) {

        return new ResponseEntity<>(getUnProcessable(ex, ErrosEnum.USUARIO_JA_CADASTRADO.toString()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorApiResponse> noResourceFoundHandle(NoResourceFoundException ex) {

        ErrorApiResponse erroResponse = ErrorApiResponseBuilder.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .erros(Collections.singletonList(ErroResponseBuilder.builder()
                        .codigo(ErrosEnum.ENDPOINT_NAO_ENCONTRADO.toString())
                        .mensagem(ex.getBody().toString())
                        .build())
                ).build();

        return new ResponseEntity<>(erroResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorApiResponse> methodValidationexceptionHandle(HandlerMethodValidationException ex) {

        ErrorApiResponse erroResponse = ErrorApiResponseBuilder.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .erros(Collections.singletonList(ErroResponseBuilder.builder()
                        .codigo(ErrosEnum.PAYLOAD_INVALIDO.toString())
                        .mensagem(ex.getBody().toString())
                        .build())
                ).build();

        return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorApiResponse> badRequestExceptionHandle(BadRequestException ex) {

        ErrorApiResponse erroResponse = ErrorApiResponseBuilder.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .erros(Collections.singletonList(ErroResponseBuilder.builder()
                        .codigo(ErrosEnum.CAMPO_OBRIGATORIO.toString())
                        .mensagem(ex.getMessage())
                        .build())
                ).build();

        return new ResponseEntity<>(erroResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorApiResponse> httpRequestMethosNotSupportefExceptionHandle(
            HttpRequestMethodNotSupportedException ex) {

        ErrorApiResponse erroResponse = ErrorApiResponseBuilder.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.toString())
                .erros(Collections.singletonList(ErroResponseBuilder.builder()
                        .codigo(ErrosEnum.METODO_NAO_SUPORTADO.toString())
                        .mensagem(ex.getMessage())
                        .build())
                ).build();

        return new ResponseEntity<>(erroResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ErrorApiResponse getNotfound(Exception ex) {

        return ErrorApiResponseBuilder.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .erros(Collections.singletonList(ErroResponseBuilder.builder()
                        .codigo(ErrosEnum.NAO_ENCONTRADO.toString())
                        .mensagem(ex.getMessage())
                        .build())
                ).build();
    }

    private ErrorApiResponse getUnProcessable(Exception ex, String statusEnum) {
        return ErrorApiResponseBuilder.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                .erros(Collections.singletonList(ErroResponseBuilder.builder()
                        .codigo(statusEnum)
                        .mensagem(ex.getMessage())
                        .build())
                ).build();
    }
}
