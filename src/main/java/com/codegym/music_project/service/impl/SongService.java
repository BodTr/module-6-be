package com.codegym.music_project.service.impl;

import com.codegym.music_project.model.Singer;
import com.codegym.music_project.model.Song;
import com.codegym.music_project.repository.ISingerRepository;
import com.codegym.music_project.repository.ISongRepository;
import com.codegym.music_project.service.interfaces_service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class SongService implements ISongService {
    @Autowired
    private ISongRepository songRepository;

    @Autowired
    private ISingerRepository singerRepository;

    @Override
    public Iterable<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public void save(Song song) {
        songRepository.save(song);
    }

    public void saveSong(String title, String description, List<Long> singerIds, MultipartFile file) throws IOException {
        System.out.println("file" + file.getOriginalFilename() + " " + file.getContentType() + " " + file.getSize());
        Song song = new Song();
        song.setTitle(title);
        song.setDescription(description);

        Set<Singer> singers = new HashSet<>(singerRepository.findAllById(singerIds));
        song.setSingers(singers);

        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
            String relativefilePath = "/uploads/" + fileName;
            Path filePath = Paths.get(System.getProperty("user.dir") + relativefilePath);
            System.out.println("filePath: " + filePath);
            file.transferTo(filePath);
            song.setSongStringLink(relativefilePath);
        }
        this.save(song);
    }



    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    public void updateSong(Long id, String title, String description, List<Long> singerIds, MultipartFile file) throws IOException {
        String relativefilePath;
        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
            relativefilePath = "/uploads/" + fileName;
            Path filePath = Paths.get(System.getProperty("user.dir") + relativefilePath);
            System.out.println("filePath: " + filePath);
            file.transferTo(filePath);

        } else {
            relativefilePath = "";
        }

        Optional<Song> song = songRepository.findById(id);
        if (song.isPresent()) {
            song.map(s -> {
                s.setTitle(title);
                s.setDescription(description);
                Set<Singer> singers = new HashSet<>(singerRepository.findAllById(singerIds));
                s.setSingers(singers);
                s.setSongStringLink(relativefilePath);
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
