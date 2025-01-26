package lk.phoenix.clients.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public record VideoUploadData(String title
        , String description
        , MultipartFile videoFile,MultipartFile thumbnail) implements Serializable {
    public VideoUploadData(MultipartFile videoFile, String title, String description) {
        this(title, description, videoFile, null);
    }
}
