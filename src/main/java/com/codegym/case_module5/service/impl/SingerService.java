package com.codegym.case_module5.service.impl;

import com.codegym.case_module5.model.Singer;
import com.codegym.case_module5.repository.ISingerRepository;
import com.codegym.case_module5.service.interfaces_service.ISingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class SingerService implements ISingerService {
    @Autowired
    private ISingerRepository singerRepository;

    @Override
    public Iterable<Singer> findAll() {
        return singerRepository.findAll();
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
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);
            System.out.println("filePath: " + filePath);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            singer.setAvatarLinkString(fileName);
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
