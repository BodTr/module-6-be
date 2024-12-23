package com.codegym.music_project.repository;

import com.codegym.music_project.dto.SongBySingerDTO;
import com.codegym.music_project.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISongRepository extends JpaRepository<Song, Long> {
    // Tìm tất cả bài hát theo singerId
    // s.id, s.title, s.description, s.songStringLink
//    SongBySingerDTO songdto = new SongBySingerDTO(String id, );
    @Query("SELECT new com.codegym.music_project.dto.SongBySingerDTO(s.id, s.title, s.description, s.songStringLink) FROM Song s JOIN s.singers si WHERE si.id = :singerId")
    List<SongBySingerDTO> findAllBySingerId(@Param("singerId") Long singerId);
}
