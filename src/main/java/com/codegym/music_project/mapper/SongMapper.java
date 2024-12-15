package com.codegym.music_project.mapper;

import com.codegym.music_project.dto.SongDTO;
import com.codegym.music_project.model.Song;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper extends Converter<Song, SongDTO> {

    SongMapper INSTANCE = Mappers.getMapper( SongMapper.class );

    @Named(value = "useMe")
    SongDTO entityToDomain(Song song);

    @IterableMapping(qualifiedByName = "useMe")
    List<SongDTO> entitiesToDomains(List<Song> songs);

}
