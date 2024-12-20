package com.codegym.music_project.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
@RequestMapping("/uploads")
public class FileUpLoadTController {

    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> fileUpLoad(@PathVariable String filename) throws IOException {
//        System.out.println("file name: "+ filename);
        Path filePath = Paths.get(System.getProperty("user.dir") + "/uploads").resolve(filename);
//        System.out.println("file path: "+ filePath);
        Resource resource = new UrlResource(filePath.toUri());
//        System.out.println("file resource: "+ resource);
        if (resource.exists() || resource.isReadable()) {

            String mimeType = Files.probeContentType(filePath);
            System.out.println("mimeType: " + mimeType);
            if (mimeType.startsWith("image/")) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.parseMediaType("audio/mpeg"))
                        .body(resource);
            }
        } else {
            System.out.println("lỗi ở đây, FileUpLoadTController");
            throw new RuntimeException("File not found");
        }
    }
}
