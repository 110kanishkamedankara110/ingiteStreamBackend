package lk.phoenix.videoProcessor.controller;

import lk.phoenix.clients.dto.VideoProcessRequest;
import lk.phoenix.clients.dto.VideoProcessorResponce;
import lk.phoenix.videoProcessor.service.Processor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/processVideo")
public class ProcessVideo {

    private Processor processor;

    public ProcessVideo(Processor processor) {
        this.processor = processor;
    }

    @PostMapping("/process")
    public VideoProcessorResponce processVideo(@RequestBody VideoProcessRequest request) throws IOException {
        return processor.process(request);
    }
}
