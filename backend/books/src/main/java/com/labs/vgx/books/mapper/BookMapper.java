package com.labs.vgx.books.mapper;

import com.labs.vgx.books.constants.BookConstants;
import com.labs.vgx.books.models.BookApiResponse;
import com.labs.vgx.books.models.BuscandoListaFiltroLivros200Response;
import com.labs.vgx.books.models.entityes.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring", imports = {
        BookConstants.class
})
public interface BookMapper {
    BookApiResponse bookEntityToApiResponse(BookEntity bookEntity);

    @Mapping(source = "bookEntities.content", target = "books")
    BuscandoListaFiltroLivros200Response pageBookEntityToBuscandoListaPaginadaBook200Response(PageImpl<BookEntity> bookEntities);
}
