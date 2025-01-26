package lk.phoenix.uploaderService.controller;

import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("api/v1/uploaderService")
public class Fetch {
    @Value("${application.root-path}")
    String root;

    @GetMapping("/fetchImage")
    public ResponseEntity<Resource> fetchImage(@RequestParam("name") String name) {
        return fetch(name,"image");
    }

    @GetMapping("/fetchVideo")
    public ResponseEntity<Resource> fetchVideo(@RequestParam("name") String name) {
        return fetch(name,"video");
    }

    private ResponseEntity<Resource> fetch(String name,String type){
        File f=switch (type) {
            case "image" -> new File(root + "/image/" + name);
            case "video" -> new File(root + "/video/" + name);
            default -> null;
        };

        FileSystemResource file = new FileSystemResource(f);
        String[] ext=f.getName().split("\\.");
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("video/"+ext[ext.length-1]))
                .body(file);
    }

}
