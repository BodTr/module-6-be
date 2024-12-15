package com.codegym.music_project.service.impl;

import com.codegym.music_project.dto.SingerDTO;
import com.codegym.music_project.model.Singer;
import com.codegym.music_project.repository.ISingerRepository;
import com.codegym.music_project.service.interfaces_service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SingerService implements ISingerService {
    @Autowired
    private ISingerRepository singerRepository;

    @Override
    public List<Singer> findAll() {
        return singerRepository.findAll();
    }

    public List<SingerDTO> findAllSinger() {
        List<Singer> singers = this.findAll();
        return singers.stream().map(singer -> new SingerDTO(
                singer.getId(),
                singer.getName(),
                singer.getAvatarLinkString()
        )).collect(Collectors.toList());
    }

    @Override
    public void save(Singer singer) {
        singerRepository.save(singer);
    }


    public void saveSinger(String name, MultipartFile file) throws IOException {
        System.out.println("file" + file.getOriginalFilename() + " " + file.getContentType() + " " + file.getSize());
        Singer singer = new Singer();
        singer.setName(name);

        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "_");
            String filePath = "/uploads/" + fileName;
            Path path = Paths.get(System.getProperty("user.dir") + filePath);
            file.transferTo(path);
            singer.setAvatarLinkString(filePath); // Đường dẫn đúng để hiển thị

        }


        this.save(singer);
    }

    @Override
    public Optional<Singer> findById(Long id) {
        return singerRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        singerRepository.deleteById(id);
    }

}
