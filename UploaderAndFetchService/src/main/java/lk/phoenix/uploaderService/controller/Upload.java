package lk.phoenix.uploaderService.controller;

import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.uploaderService.service.UploaderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/uploaderService")
public class Upload {

    public Upload(UploaderService uploaderService) {
        this.uploaderService = uploaderService;
    }

    private UploaderService uploaderService;

    @PostMapping("/upload")
    public UploadResponse upload(@RequestParam("file") MultipartFile file){
        return uploaderService.upload(file);
    }
}
