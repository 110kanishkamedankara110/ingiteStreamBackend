package lk.phoenix.videoStream.service;

import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.dto.VideoProcessRequest;
import lk.phoenix.clients.dto.VideoUploadData;
import lk.phoenix.clients.uploaderService.Uploader;
import lk.phoenix.rabbitMqMessaging.RabbitMqMessageProducer;
import lk.phoenix.videoStream.model.Video;
import lk.phoenix.videoStream.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoUploaderService {

    public VideoUploaderService(RabbitMqMessageProducer producer, Uploader uploader, VideoRepository videoRepository) {
        this.uploader = uploader;
        this.videoRepository = videoRepository;
        this.rabbitMqMessageProducer = producer;
    }

    private final Uploader uploader;
    private final VideoRepository videoRepository;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;

    public UploadResponse upload(VideoUploadData videoUploadData) {
        UploadResponse response = uploader.upload(videoUploadData.videoFile());
        if (response.status() == 200) {
            Video v = new Video();
            v.setDescription(videoUploadData.description());
            v.setTitle(videoUploadData.title());
            v.setStatus("Processing");
            v.setUrl(response.location());
            Video vid = videoRepository.saveAndFlush(v);
            rabbitMqMessageProducer.publish(new VideoProcessRequest(vid.getId().intValue(), vid.getUrl()), "internal.exchange", "igniteStream.processor.process");
        }
        return response;
    }
}
