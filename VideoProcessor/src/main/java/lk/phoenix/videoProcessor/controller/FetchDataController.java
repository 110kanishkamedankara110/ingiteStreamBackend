package lk.phoenix.videoProcessor.controller;

import lk.phoenix.videoProcessor.model.VideoProcessedData;
import lk.phoenix.videoProcessor.service.FetchDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/processVideo")
public class FetchDataController {

    private final FetchDataService fetchDataService;

    public FetchDataController(FetchDataService fetchDataService) {
        this.fetchDataService = fetchDataService;
    }
    @GetMapping("/fetchById")
    public VideoProcessedData fetchById(@RequestParam int id) {
        return fetchDataService.fetchDataByIdVideo(id);
    }
}
