package com.tsystems.dao.implementation;

import com.tsystems.dao.api.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * A generic DAO implementation of CRUD operations
 */
public abstract class GenericDAOImpl<E, ID> implements GenericDAO<E, ID> {

    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public GenericDAOImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) type.getActualTypeArguments()[0];
    }

    /**
     * Creates an entity.
     *
     * @param entity the entity that needs to be created;
     */
    @Override
    public void add(E entity) {
        entityManager.persist(entity);
    }

    /**
     * Gets an entity.
     *
     * @param id the id of entity which needs to be read;
     * @return E;
     */
    @Override
    public E findById(ID id) {
        return entityManager.getReference(entityClass, id);
    }

    /**
     * Updates an entity.
     *
     * @param entity the entity that needs to be updated;
     */
    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    /**
     * Deletes an entity.
     *
     * @param entity the entity which needs to be deleted;
     */
    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    /**
     * Gets the list of entities.
     *
     * @return List<E>;
     */
    @Override
    public List<E> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
