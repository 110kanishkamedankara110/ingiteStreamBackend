package lk.phoenix.clients.uploaderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.uploaderService.component.Fallback;
import lk.phoenix.clients.uploaderService.config.UploaderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "uploaderService",
        path = "api/v1/uploaderService",
        configuration = UploaderConfig.class,
        fallback = Fallback.class
)
public interface Uploader {
    @CircuitBreaker(name = "uploaderService", fallbackMethod = "uploadFallback")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadResponse upload(@RequestPart("file") MultipartFile file);

    default UploadResponse uploadFallback(MultipartFile file,Throwable throwable) {
        return new UploadResponse(503 ,"Video Uploader Service Unavailable Please Try Again Later","");
    }
}