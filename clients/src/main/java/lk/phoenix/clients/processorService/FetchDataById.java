package lk.phoenix.clients.processorService;

import lk.phoenix.clients.dto.VideoProcessedData;
import lk.phoenix.clients.uploaderService.component.Fallback;
import lk.phoenix.clients.uploaderService.config.UploaderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "videoProcessor",
        path = "api/v1/processVideo",
        configuration = UploaderConfig.class
)
public interface FetchDataById {
    @GetMapping(value = "/fetchById")
    public VideoProcessedData fetchById(@RequestParam int id);
}
