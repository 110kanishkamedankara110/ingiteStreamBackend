package lk.phoenix.videoStream.controller;

import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.dto.VideoUploadData;
import lk.phoenix.videoStream.service.VideoUploaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("api/v1/VideoService")
public class VideoUpload {

    public VideoUpload(VideoUploaderService videoUploaderService) {
        this.videoUploaderService = videoUploaderService;
    }

    private final VideoUploaderService videoUploaderService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> upload(@ModelAttribute VideoUploadData videoUploadData) {
        UploadResponse response=videoUploaderService.upload(videoUploadData);
        return ResponseEntity.status(response.status()).body(response);

    }
}
