package com.tsystems.dao.api;

import java.util.List;

public interface GenericDAO<E, ID> {

    void add(E entity);

    E findById(ID id);

    void update(E entity);

    void delete(E entity);

    List<E> getAll();

}
