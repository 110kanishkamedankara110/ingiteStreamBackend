package lk.phoenix.clients.uploaderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lk.phoenix.clients.Exception.ServiceUnavailableException;
import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.uploaderService.config.UploaderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "uploaderAndFetchService",
        path = "api/v1/uploaderService",
        configuration = UploaderConfig.class
)
public interface UploaderService {
    @CircuitBreaker(name = "uploaderService", fallbackMethod = "uploadFallback")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadResponse upload(@RequestPart("file") MultipartFile file)throws ServiceUnavailableException;

    default UploadResponse uploadFallback(MultipartFile file,Throwable throwable)  throws ServiceUnavailableException{
        throw new ServiceUnavailableException();
    }

    @CircuitBreaker(name = "fetchService", fallbackMethod = "fetchFallback")
    @GetMapping(  "/fetchImage")
    ResponseEntity<Resource> fetchImage(@RequestParam("name") String name) throws ServiceUnavailableException;

    @CircuitBreaker(name = "fetchService", fallbackMethod = "fetchFallback")
    @GetMapping("/fetchVideo")
    ResponseEntity<Resource> fetchVideo(@RequestParam("name") String name) throws ServiceUnavailableException;

    default ResponseEntity<Resource> fetchFallback(String name, Throwable throwable)  throws ServiceUnavailableException{
        throw new ServiceUnavailableException();
    }

}