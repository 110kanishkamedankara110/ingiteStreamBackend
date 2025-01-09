package lk.phoenix.videoStream.controller;


import lk.phoenix.clients.dto.VideoProcessedData;
import lk.phoenix.clients.processorService.FetchDataById;
import lk.phoenix.videoStream.repository.VideoRepository;
import lk.phoenix.videoStream.service.VideoRetreveService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/VideoService")
public class Video {

   private final VideoRetreveService videoRetreveService;

    public Video(VideoRetreveService videoRetreveService) {
        this.videoRetreveService = videoRetreveService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/videos")
    public List<Map<String, String>> getVideos() {
        return videoRetreveService.getVideos();
    }
}
