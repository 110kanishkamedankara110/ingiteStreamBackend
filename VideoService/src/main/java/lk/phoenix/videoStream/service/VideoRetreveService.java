package lk.phoenix.videoStream.service;

import lk.phoenix.clients.dto.VideoProcessedData;
import lk.phoenix.clients.processorService.FetchDataById;
import lk.phoenix.videoStream.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service

public class VideoRetreveService {

    private final VideoRepository videoRepository;
    private final FetchDataById fetchDataById;

    public VideoRetreveService(VideoRepository videoRepository, FetchDataById fetchDataById) {
        this.videoRepository = videoRepository;
        this.fetchDataById = fetchDataById;
    }

    public List<Map<String,String>> getVideos(){
        List<Map<String, String>> map = new LinkedList<>();
        List<lk.phoenix.videoStream.model.Video> videos = videoRepository.getAll();
        videos.forEach(video -> {
            VideoProcessedData data = fetchDataById.fetchById(video.getId().intValue());

            Map<String, String> v = new HashMap<>();
            v.put("title", video.getTitle());
            v.put("description", video.getDescription());
            v.put("video", video.getUrl());
            v.put("id", video.getId().toString());
            if (data != null) {
                v.put("image",data.imageUrl());
                v.put("primaryColor",data.primaryColor());
                v.put("secondaryColor",data.secondaryColor());
            }
            map.add(v);
        });
        return map;
    }
}
