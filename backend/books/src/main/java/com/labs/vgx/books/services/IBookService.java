package com.labs.vgx.books.services;

import com.labs.vgx.books.models.BookApiRequest;
import com.labs.vgx.books.models.BookApiResponse;
import com.labs.vgx.books.models.BuscandoListaFiltroLivros200Response;

public interface IBookService {
    BookApiResponse alterandoLivro(String guid, BookApiRequest bookApiRequest);
    void apagandoLivro(String guid);
    BuscandoListaFiltroLivros200Response buscandoListaFiltroLivros(Integer page, Integer size, String title);
    BookApiResponse buscandoLivroPeloGUID(String guid);
    BookApiResponse criandoBook(BookApiRequest bookApiRequest);
}
