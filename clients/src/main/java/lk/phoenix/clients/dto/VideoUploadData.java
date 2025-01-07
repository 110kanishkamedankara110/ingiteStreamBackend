package lk.phoenix.clients.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public record VideoUploadData(String title
        , String description
        , MultipartFile videoFile) implements Serializable {
}
