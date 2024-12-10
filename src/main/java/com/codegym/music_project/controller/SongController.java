package com.codegym.music_project.controller;

import com.codegym.music_project.model.Song;
import com.codegym.music_project.service.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Iterable<Song> getAllSongs() {
        return songService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSong(@RequestParam String title, @RequestParam String description, @RequestParam List<Long> singerIds, @RequestParam("songFile") MultipartFile songFile) throws IOException {
        System.out.println("đã đến createSong()");
        songService.saveSong(title, description, singerIds, songFile);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSong(@PathVariable Long id, @RequestParam String title, @RequestParam String description, @RequestParam List<Long> singerIds, @RequestParam("songFileEdit") MultipartFile songFile) throws IOException {
        songService.updateSong(id, title, description, singerIds, songFile);
    }
}
