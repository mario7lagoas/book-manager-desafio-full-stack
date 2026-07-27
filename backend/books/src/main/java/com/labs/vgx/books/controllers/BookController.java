package com.labs.vgx.books.controllers;

import com.labs.vgx.books.models.BookApiRequest;
import com.labs.vgx.books.models.BookApiResponse;
import com.labs.vgx.books.models.BuscandoListaFiltroLivros200Response;

import com.labs.vgx.books.services.BookFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController implements BooksApiDelegate {

    private final BookFacade bookFacade;

    public BookController(final BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @Override
    public ResponseEntity<BookApiResponse> alterandoLivro(String guid, BookApiRequest bookApiRequest) {
        return new ResponseEntity<BookApiResponse>(this.bookFacade.alterandoLivro(guid, bookApiRequest),
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Void> apagandoLivro(String guid) {
        this.bookFacade.apagandoLivro(guid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<BuscandoListaFiltroLivros200Response> buscandoListaFiltroLivros(Integer page, Integer size, String title) {
        return new ResponseEntity<BuscandoListaFiltroLivros200Response>(this.bookFacade.buscandoListaFiltroLivros(page, size, title), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookApiResponse> buscandoLivroPeloGUID(String guid) {
        return new ResponseEntity<BookApiResponse>(this.bookFacade.buscandoLivroPeloGUID(guid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookApiResponse> criandoBook(BookApiRequest bookApiRequest) {
        return new ResponseEntity<BookApiResponse>(this.bookFacade.criandoBook(bookApiRequest), HttpStatus.CREATED);
    }
}
