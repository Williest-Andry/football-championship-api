package com.williest.onechampionshipapi.repository.crudOperation;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EntityDAO<T>{
    T findById(UUID id);

    T save(T entity);

    List<T> saveAll(List<T> entities);

    T update(T entity);

    T deleteById(UUID id);
}
