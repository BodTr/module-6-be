package com.codegym.music_project.controller;

import com.codegym.music_project.dto.SongDTO;
import com.codegym.music_project.service.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/song")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SongDTO> getAllSongs() {
        return songService.getAllSong();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSong(@RequestParam String title, @RequestParam String description, @RequestParam List<Long> singers, @RequestParam("songFile") MultipartFile songFile) throws IOException {
        System.out.println("đã đến createSong()");
        songService.saveSong(title, description, singers, songFile);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSong(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(songService.findByIdDTO(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSong(@PathVariable Long id, @RequestParam String title, @RequestParam String description, @RequestParam List<Long> singers, @RequestParam(value = "songFileEdit", required = false) MultipartFile songFile) throws IOException {
        songService.updateSong(id, title, description, singers, songFile);
    }
}
