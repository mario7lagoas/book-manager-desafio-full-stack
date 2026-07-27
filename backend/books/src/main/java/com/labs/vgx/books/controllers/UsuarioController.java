package com.labs.vgx.books.controllers;

import com.labs.vgx.books.models.UsuarioApiRequest;
import com.labs.vgx.books.services.BookFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController implements AuthApiDelegate {

    private final BookFacade bookFacade;

    public UsuarioController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @Override
    public ResponseEntity<Void> criandoUsuario(UsuarioApiRequest usuarioApiRequest) {
        bookFacade.criandoUsuario(usuarioApiRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
