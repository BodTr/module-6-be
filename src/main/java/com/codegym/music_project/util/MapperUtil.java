package com.codegym.music_project.util;

import com.codegym.music_project.dto.SingerDTO;
import com.codegym.music_project.dto.SongDTO;
import com.codegym.music_project.model.Singer;
import com.codegym.music_project.model.Song;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface MapperUtil {
    SongDTO songToSongDTO(Song song);
    SingerDTO singerToSingerDTO(Singer singer);
    List<SingerDTO> singersToSingerDTOs(List<Singer> singers);
    List<SongDTO> songsToSongDTOs(List<Song> songs);
}
