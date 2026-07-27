package com.labs.vgx.books.services;

import com.labs.vgx.books.models.BookApiRequest;
import com.labs.vgx.books.models.BookApiResponse;
import com.labs.vgx.books.models.BuscandoListaFiltroLivros200Response;
import com.labs.vgx.books.models.UsuarioApiRequest;
import org.springframework.stereotype.Component;

@Component
public class BookFacade {

    private final IBookService bookService;
    private final IUsuarioService usuarioService;

    public BookFacade(IBookService bookService, IUsuarioService usuarioService, IUsuarioService usuarioService1) {
        this.bookService = bookService;
        this.usuarioService = usuarioService1;
    }

    public void apagandoLivro(String guid) {
        this.bookService.apagandoLivro(guid);
    }

    public BuscandoListaFiltroLivros200Response buscandoListaFiltroLivros(Integer page, Integer size, String title) {
        return this.bookService.buscandoListaFiltroLivros(page, size, title);
    }

    public BookApiResponse buscandoLivroPeloGUID(String guid) {
        return this.bookService.buscandoLivroPeloGUID(guid);
    }

    public BookApiResponse criandoBook(BookApiRequest bookApiRequest) {
        return this.bookService.criandoBook(bookApiRequest);
    }

    public BookApiResponse alterandoLivro(String guid, BookApiRequest bookApiRequest) {
        return this.bookService.alterandoLivro(guid, bookApiRequest);
    }

    public void criandoUsuario(UsuarioApiRequest usuarioApiRequest) {
        this.usuarioService.criandoUsuario(usuarioApiRequest);
    }
}
