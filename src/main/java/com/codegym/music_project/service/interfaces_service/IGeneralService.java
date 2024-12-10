package com.codegym.music_project.service.interfaces_service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    void save(T t);
    Optional<T> findById(Long id);
    void remove(Long id);
}
