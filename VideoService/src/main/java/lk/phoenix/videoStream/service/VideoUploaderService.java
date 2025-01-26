package lk.phoenix.videoStream.service;

import lk.phoenix.clients.Exception.ServiceUnavailableException;
import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.dto.VideoProcessRequest;
import lk.phoenix.clients.dto.VideoUploadData;
import lk.phoenix.clients.uploaderService.UploaderService;
import lk.phoenix.rabbitMqMessaging.RabbitMqMessageProducer;
import lk.phoenix.videoStream.model.Video;
import lk.phoenix.videoStream.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoUploaderService {

    public VideoUploaderService(RabbitMqMessageProducer producer, UploaderService uploader, VideoRepository videoRepository) {
        this.uploader = uploader;
        this.videoRepository = videoRepository;
        this.rabbitMqMessageProducer = producer;
    }

    private final UploaderService uploader;
    private final VideoRepository videoRepository;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;

    public UploadResponse upload(VideoUploadData videoUploadData) {
        UploadResponse response = null;
        try {
            response = uploader.upload(videoUploadData.videoFile());
        } catch (ServiceUnavailableException e) {
            return new UploadResponse(503,"Uploader Service Unavailable Please Try Again Later","");
        }
        if (response.status() == 200) {
            Video v = new Video();
            v.setDescription(videoUploadData.description());
            v.setTitle(videoUploadData.title());
            v.setStatus("Processing");
            v.setUrl(response.location());
            Video vid = videoRepository.saveAndFlush(v);

            String thumbnail=null;
            if(videoUploadData.thumbnail()!=null) {
                UploadResponse resp= null;
                try {
                    resp = uploader.upload(videoUploadData.thumbnail());
                } catch (ServiceUnavailableException e) {
                    return new UploadResponse(503,"Uploader Service Unavilavle Plese Try Again Later","");
                }
                thumbnail=resp.location();
            }

            rabbitMqMessageProducer.publish(new VideoProcessRequest(vid.getId().intValue(), vid.getUrl(),thumbnail), "internal.exchange", "igniteStream.processor.process");
        }
        return response;
    }
}
