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
public class Play {
    @GetMapping("/play")
    public ResponseEntity<Resource> play(@RequestParam("v") String location) {
        File videoFile = new File(location);
        FileSystemResource file = new FileSystemResource(videoFile);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(file);
    }

}
