package com.williest.onechampionshipapi.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EntityService<T>{
    T getById(String id);

    T save(T entity);

    List<T> saveAll(List<T> entities);

    T delete(T entity);
}
