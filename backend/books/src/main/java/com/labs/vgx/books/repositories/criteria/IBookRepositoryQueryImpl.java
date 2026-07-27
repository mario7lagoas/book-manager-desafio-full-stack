package com.labs.vgx.books.repositories.criteria;

import com.labs.vgx.books.mapper.BookMapper;
import com.labs.vgx.books.models.BuscandoListaFiltroLivros200Response;
import com.labs.vgx.books.models.entityes.BookEntity;
import com.labs.vgx.books.models.filter.BookFiltro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IBookRepositoryQueryImpl implements IBookRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private BookMapper mapper;

    @Override
    public BuscandoListaFiltroLivros200Response filtrar(BookFiltro bookFiltro, Pageable page) {
        From<?, ?> orderByFromEntity = null;
        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> criteriaQuery = builder.createQuery(BookEntity.class);

        Root<BookEntity> root = criteriaQuery.from(BookEntity.class);

        Predicate[] predicates = criarRestricted(bookFiltro, builder, root);
        criteriaQuery.where(predicates);

        orderByFromEntity = root;

        List<Order> orderList = new ArrayList();

        if (orderByFromEntity != null) {
            orderList.add(builder.asc(orderByFromEntity.get("title")));
            orderList.add(builder.desc(orderByFromEntity.get("dataCadastro")));
        }
        criteriaQuery.orderBy(orderList);

        TypedQuery<BookEntity> query = this.manager.createQuery(criteriaQuery);

        additionalRestrictedDePaginate(query, page);

        return this.mapper.pageBookEntityToBuscandoListaPaginadaBook200Response(
                new PageImpl<>(query.getResultList(), page, total(bookFiltro)));

    }

    private Long total(BookFiltro bookFiltro) {
        CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<BookEntity> root = criteria.from(BookEntity.class);

        Predicate[] predicates = criarRestricted(bookFiltro, builder, root);
        criteria.where(predicates);
        criteria.select(builder.count(root));
        return this.manager.createQuery(criteria).getSingleResult();
    }

    private void additionalRestrictedDePaginate(TypedQuery<BookEntity> query, Pageable page) {
        int pagingActual = page.getPageNumber();
        int totalDeRegistryForPaging = page.getPageSize();
        int primerRegistryDaPaging = pagingActual * totalDeRegistryForPaging;

        query.setFirstResult(primerRegistryDaPaging);
        query.setMaxResults(totalDeRegistryForPaging);
    }

    private Predicate[] criarRestricted(BookFiltro bookFiltro, CriteriaBuilder builder, Root<BookEntity> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(bookFiltro.getTitle())) {
            predicates.add(builder.like(
                    builder.lower(root.get("title")), "%"
                            + bookFiltro.getTitle()
                            .toUpperCase() + "%")
            );
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
