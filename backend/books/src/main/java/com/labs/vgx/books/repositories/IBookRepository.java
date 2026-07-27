package com.labs.vgx.books.repositories;

import com.labs.vgx.books.models.entityes.BookEntity;
import com.labs.vgx.books.repositories.criteria.IBookRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<BookEntity, Long>, IBookRepositoryQuery {

    Optional<BookEntity> findByGuid(String guid);
    Optional<BookEntity> findByTitle(String title);
 }
