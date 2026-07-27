package com.labs.vgx.books.repositories.criteria;

import com.labs.vgx.books.models.BuscandoListaFiltroLivros200Response;
import com.labs.vgx.books.models.filter.BookFiltro;
import org.springframework.data.domain.Pageable;

public interface IBookRepositoryQuery {
    BuscandoListaFiltroLivros200Response filtrar(BookFiltro bookFiltro, Pageable page);
}
