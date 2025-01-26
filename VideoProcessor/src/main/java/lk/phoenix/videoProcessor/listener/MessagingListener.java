package lk.phoenix.videoProcessor.listener;

import lk.phoenix.clients.dto.VideoProcessRequest;
import lk.phoenix.clients.dto.VideoProcessorResponce;
import lk.phoenix.rabbitMqMessaging.RabbitMqMessageProducer;
import lk.phoenix.videoProcessor.service.Processor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class MessagingListener {
    private final Processor processor;
    private final RabbitMqMessageProducer rabbitMqMessageProducer;

    public MessagingListener(Processor processor,RabbitMqMessageProducer rabbitMqMessageProducer) {
        this.processor = processor;
        this.rabbitMqMessageProducer = rabbitMqMessageProducer;
    }

    @RabbitListener(queues ="${rabbitmq.queues.notification}")
    public void consumer(VideoProcessRequest videoProcessRequest) throws Exception {
       VideoProcessorResponce response=processor.process(videoProcessRequest);
        rabbitMqMessageProducer.publish(response,"internal.exchange","igniteStream.videoService.processed");
    }
}
