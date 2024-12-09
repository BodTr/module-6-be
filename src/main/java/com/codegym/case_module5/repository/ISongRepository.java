package com.codegym.case_module5.repository;

import com.codegym.case_module5.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends JpaRepository<Song, Long> {
}
