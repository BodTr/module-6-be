package com.codegym.case_module5.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
public class FileUpLoadTController {
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> fileUpLoad(@PathVariable String filename) throws MalformedURLException {
        System.out.println("file name: "+ filename);
        Path filePath = Paths.get(System.getProperty("user.dir") + "/uploads").resolve(filename);
        System.out.println("file path: "+ filePath);
        Resource resource = new UrlResource(filePath.toUri());
        System.out.println("file resource: "+ resource);
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            System.out.println("lỗi ở đây, FileUpLoadTController");
            throw new RuntimeException("File not found");
        }
    }
}
