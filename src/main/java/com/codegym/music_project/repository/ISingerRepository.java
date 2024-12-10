package com.codegym.music_project.repository;

import com.codegym.music_project.model.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISingerRepository extends JpaRepository<Singer, Long> {
}
