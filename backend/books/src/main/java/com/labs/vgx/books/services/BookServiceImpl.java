package com.labs.vgx.books.services;

import com.labs.vgx.books.exceptions.BadRequestException;
import com.labs.vgx.books.exceptions.BookCadastradaException;
import com.labs.vgx.books.exceptions.BookNaoEncontradoException;
import com.labs.vgx.books.mapper.BookMapper;
import com.labs.vgx.books.models.BookApiRequest;
import com.labs.vgx.books.models.BookApiResponse;
import com.labs.vgx.books.models.BuscandoListaFiltroLivros200Response;
import com.labs.vgx.books.models.entityes.BookEntity;
import com.labs.vgx.books.models.filter.BookFiltro;
import com.labs.vgx.books.repositories.IBookRepository;
import com.labs.vgx.books.utils.BookUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class BookServiceImpl implements IBookService {
    private final IBookRepository iBookRepository;
    private final BookMapper mapper;
    private final BookUtil bookUtil;

    public BookServiceImpl(IBookRepository iBookRepository, BookMapper mapper, BookUtil bookUtil) {
        this.iBookRepository = iBookRepository;
        this.mapper = mapper;
        this.bookUtil = bookUtil;
    }

    @Override
    @Transactional
    public BookApiResponse alterandoLivro(String guid, BookApiRequest bookApiRequest) {
        BookEntity bookEntity = this.iBookRepository.findByGuid(guid)
                .orElseThrow(() -> new BookNaoEncontradoException("Livro não encontrado."));

        if (this.bookUtil.checkDataNullAndEmpty(bookApiRequest.getTitle())) {
            bookEntity.setTitle(bookApiRequest.getTitle());
        }

        if (this.bookUtil.checkDataNullAndEmpty(bookApiRequest.getAuthor())) {
            bookEntity.setAuthor(bookApiRequest.getAuthor());
        }

        if (this.bookUtil.checkDataNullAndEmpty(bookApiRequest.getDescription())) {
            bookEntity.setDescription(bookApiRequest.getDescription());
        }

        if (bookApiRequest.getYear() != null) {
            bookEntity.setYear(bookApiRequest.getYear().intValue());
        }
        return this.mapper.bookEntityToApiResponse(this.iBookRepository.save(bookEntity));
    }

    @Override
    @Transactional
    public void apagandoLivro(String guid) {
        BookEntity bookEntity = this.iBookRepository.findByGuid(guid)
                .orElseThrow(() -> new BookNaoEncontradoException("Livro não encontrado."));
        this.iBookRepository.delete(bookEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public BuscandoListaFiltroLivros200Response buscandoListaFiltroLivros(Integer page, Integer size, String title) {
        BookFiltro bookFiltro = BookFiltro.builder()
                .title(title)
                .build();

        return this.iBookRepository.filtrar(bookFiltro, PageRequest.of(page, size));
    }

    @Override
    @Transactional(readOnly = true)
    public BookApiResponse buscandoLivroPeloGUID(String guid) {
        BookEntity bookEntity = this.iBookRepository.findByGuid(guid)
                .orElseThrow(() -> new BookNaoEncontradoException("Livro não encontrado."));
        return this.mapper.bookEntityToApiResponse(bookEntity);
    }

    @Override
    @Transactional
    public BookApiResponse criandoBook(BookApiRequest bookApiRequest) {

        if (this.iBookRepository.findByTitle(bookApiRequest.getTitle()).isPresent()) {
            throw new BookCadastradaException("Livro já cadastrado.");
        }

        if (!this.bookUtil.checkDataNullAndEmpty(bookApiRequest.getTitle())) {
            throw new BadRequestException("Título do livro não pode ser nulo ou vazio.");
        }
        if (!this.bookUtil.checkDataNullAndEmpty(bookApiRequest.getAuthor())) {
            throw new BadRequestException("Autor do livro não pode ser nulo ou vazio.");
        }

        BookEntity bookEntity = BookEntity.builder()
                .guid(this.bookUtil.generateGuid())
                .title(bookApiRequest.getTitle())
                .author(bookApiRequest.getAuthor())
                .description(bookApiRequest.getDescription())
                .year(bookApiRequest.getYear().intValue())
                .build();
        return this.mapper.bookEntityToApiResponse(this.iBookRepository.save(bookEntity));
    }
}
