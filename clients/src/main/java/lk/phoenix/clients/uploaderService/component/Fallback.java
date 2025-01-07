package lk.phoenix.clients.uploaderService.component;

import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.uploaderService.Uploader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Fallback implements Uploader {
    @Override
    public UploadResponse upload(MultipartFile file) {
        return new UploadResponse(404,"Service Unavilable","");
    }
}
