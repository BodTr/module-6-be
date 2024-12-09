package com.codegym.case_module5.repository;

import com.codegym.case_module5.model.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISingerRepository extends JpaRepository<Singer, Long> {
}
