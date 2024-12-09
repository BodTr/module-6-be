package com.codegym.case_module5.service.impl;

import com.codegym.case_module5.model.Singer;
import com.codegym.case_module5.model.Song;
import com.codegym.case_module5.repository.ISingerRepository;
import com.codegym.case_module5.repository.ISongRepository;
import com.codegym.case_module5.service.interfaces_service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
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
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);
            System.out.println("filePath: " + filePath);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            song.setSongStringLink(fileName);
        }
        this.save(song);
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        songRepository.deleteById(id);
    }
}
