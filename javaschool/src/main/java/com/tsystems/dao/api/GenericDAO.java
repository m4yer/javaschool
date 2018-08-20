package com.tsystems.dao.api;

import java.util.List;

/**
 * A generic for all CRUD operations and a getAll operation.
 * These methods must exist in every DAO class.
 */
public interface GenericDAO<E, ID> {

    /**
     * Creates an entity.
     *
     * @param entity the entity that needs to be created;
     */
    void add(E entity);

    /**
     * Gets an entity.
     *
     * @param id the id of entity which needs to be read;
     * @return E;
     */
    E findById(ID id);

    /**
     * Updates an entity.
     *
     * @param entity the entity that needs to be updated;
     */
    void update(E entity);

    /**
     * Deletes an entity.
     *
     * @param entity the entity which needs to be deleted;
     */
    void delete(E entity);

    /**
     * Gets the list of entities.
     *
     * @return List<E>;
     */
    List<E> getAll();

}
