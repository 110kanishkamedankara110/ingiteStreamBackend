package lk.phoenix.uploaderService.service;

import lk.phoenix.clients.dto.UploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class UploaderService {
    public UploadResponse upload(MultipartFile file) {
        String filePath = System.currentTimeMillis()+file.getOriginalFilename();

        File parent=new File("igniterStream");
        if(!parent.exists()){
            parent.mkdir();
        }
        File content=isVideo(Objects.requireNonNull(file.getContentType()))?new File(parent,"video"):new File(parent,"image");
        if(!content.exists()){
            content.mkdir();
        }

        File destinationFile = new File(content.getAbsolutePath()+"/"+filePath);
        try {
            file.transferTo(destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new UploadResponse(200,"success",destinationFile.getAbsolutePath());
    }

    private static boolean isVideo(String contentType) {
        return contentType.startsWith("video/");
    }

    private static boolean isImage(String contentType) {
        return contentType.startsWith("image/");
    }
}
