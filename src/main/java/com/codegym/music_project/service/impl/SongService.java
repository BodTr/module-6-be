package com.codegym.music_project.service.impl;

import com.codegym.music_project.dto.SongDTO;
import com.codegym.music_project.model.Singer;
import com.codegym.music_project.model.Song;
import com.codegym.music_project.repository.ISingerRepository;
import com.codegym.music_project.repository.ISongRepository;
import com.codegym.music_project.service.interfaces_service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongService implements ISongService {
    private final ISongRepository songRepository;

    private final ISingerRepository singerRepository;

//    private final MapperUtil mapperUtil;

    @Autowired
    ConversionService conversionService;

    @Autowired
    public SongService(ISongRepository songRepository, ISingerRepository singerRepository) {
        this.songRepository = songRepository;
        this.singerRepository = singerRepository;
//        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<Song> findAll() {
        return songRepository.findAll();

    }

    public List<SongDTO> getAllSong() {
        List<Song> songs = this.findAll();
        System.out.println("songs: " + songs.size());
        List<SongDTO> songDTOs = songs.stream().map(a -> conversionService.convert(a, SongDTO.class)).collect(Collectors.toList());
        System.out.println("songDTOs: " + songDTOs.size());
        return songDTOs;
    }

    @Override
    public void save(Song song) {
        songRepository.save(song);
    }

    public void saveSong(String title, String description, List<Long> singerIds, MultipartFile file) throws IOException {
        System.out.println("file" + file.getOriginalFilename() + " " + file.getContentType() + " " + file.getSize() + " ");
        Song song = new Song();
        song.setTitle(title);
        song.setDescription(description);

        Set<Singer> singers = new HashSet<>(singerRepository.findAllById(singerIds));
        song.setSingers(singers);

        if (!file.isEmpty()) {

            String fileName;
            if ("audio/mpeg".equals(file.getContentType())) {
                fileName = UUID.randomUUID() + "_audio_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
            } else {
                fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
            }
            String  relativefilePath = "/uploads/" + fileName;
            Path filePath = Paths.get(System.getProperty("user.dir") + relativefilePath);
            System.out.println("filePath: " + filePath);
            file.transferTo(filePath);
            song.setSongStringLink(relativefilePath);
        }
        this.save(song);
    }

    public SongDTO findByIdDTO(Long id) {
        Song song = songRepository.findById(id).orElse(null);
        return conversionService.convert(song, SongDTO.class);
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    public void updateSong(Long id, String title, String description, List<Long> singerIds, MultipartFile file) throws IOException {
        String relativefilePath;
        System.out.println("file: " + file);
        if (file == null) {
            relativefilePath = "";
        } else {
            if (!file.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replace(" ", "_");
                relativefilePath = "/uploads/" + fileName;
                Path filePath = Paths.get(System.getProperty("user.dir") + relativefilePath);
                System.out.println("filePath: " + filePath);
                file.transferTo(filePath);

            } else {
                relativefilePath = "";
            }
        }


        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            if (relativefilePath.isEmpty()) {
                relativefilePath = song.get().getSongStringLink();
            }

            String finalRelativefilePath = relativefilePath;
            song.map(s -> {
                s.setTitle(title);
                s.setDescription(description);
                Set<Singer> singers = new HashSet<>(singerRepository.findAllById(singerIds));
                s.setSingers(singers);
                s.setSongStringLink(finalRelativefilePath);
                return songRepository.save(s);
            });
        } else {
            System.out.println("song not found");
            throw new NullPointerException("song not found");

        }
    }

    @Override
    public void remove(Long id) {
        songRepository.deleteById(id);
    }
}
