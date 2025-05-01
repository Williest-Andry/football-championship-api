package com.williest.onechampionshipapi.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface EntityService<T>{
    T getById(UUID id);

    T save(T entity);

    T delete(T entity);
}
