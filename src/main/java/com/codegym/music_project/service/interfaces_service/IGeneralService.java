package com.codegym.music_project.service.interfaces_service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {
    List<T> findAll();
    void save(T t);
    Optional<T> findById(Long id);
    void remove(Long id);
}
