package com.codegym.music_project.controller;

import com.codegym.music_project.model.Singer;
import com.codegym.music_project.service.impl.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Singer> getAllSingers() {
        return singerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSinger(@RequestParam String name, @RequestParam("singerAvatar") MultipartFile singerAvatar ) throws IOException {
        singerService.saveSinger(name, singerAvatar);
    }



}
