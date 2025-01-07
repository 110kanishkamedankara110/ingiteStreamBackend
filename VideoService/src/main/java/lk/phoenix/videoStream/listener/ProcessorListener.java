package lk.phoenix.videoStream.listener;

import lk.phoenix.clients.dto.VideoProcessRequest;
import lk.phoenix.clients.dto.VideoProcessorResponce;
import lk.phoenix.videoStream.repository.VideoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
@Service
public class ProcessorListener {

    public ProcessorListener(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    private final VideoRepository videoRepository;
    @RabbitListener(queues ="${rabbitmq.queues.notification}")
    public void consumer(VideoProcessorResponce videoProcessorResponce) throws IOException {
        videoRepository.updateStatusById(Integer.valueOf(videoProcessorResponce.videoId()).longValue(),"Processed");
    }
}
