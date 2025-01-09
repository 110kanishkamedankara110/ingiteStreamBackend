package lk.phoenix.videoStream.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
@RestController
@RequestMapping("api/v1/VideoService")
public class View {
    @GetMapping("/view")
    public ResponseEntity<Resource> play(@RequestParam("i") String location) {
        File imageFile = new File(location);
        FileSystemResource file = new FileSystemResource(imageFile);
        String[] ext=imageFile.getName().split("\\.");
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("image/"+ext[ext.length-1]))
                .body(file);
    }
}

