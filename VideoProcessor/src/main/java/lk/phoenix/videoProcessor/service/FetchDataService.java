package lk.phoenix.videoProcessor.service;

import lk.phoenix.videoProcessor.model.VideoProcessedData;
import lk.phoenix.videoProcessor.repository.VideoProcessedDataRepository;
import org.springframework.stereotype.Service;

@Service
public class FetchDataService {
    private final VideoProcessedDataRepository videoProcessedDataRepository;

    public FetchDataService(VideoProcessedDataRepository videoProcessedDataRepository) {
        this.videoProcessedDataRepository = videoProcessedDataRepository;
    }

    public VideoProcessedData fetchDataByIdVideo(int id){
        return videoProcessedDataRepository.findByVideoId(id);
    }
}
