package lk.phoenix.clients.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public record VideoProcessRequest(int videoId, String url, String thumbnail) implements Serializable {
    public VideoProcessRequest(int videoId, String url) {
        this(videoId, url, null);
    }
}
