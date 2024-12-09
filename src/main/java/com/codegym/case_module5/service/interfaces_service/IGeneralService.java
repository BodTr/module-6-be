package com.codegym.case_module5.service.interfaces_service;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    void save(T t);
    Optional<T> findById(Long id);
    void remove(Long id);
}
