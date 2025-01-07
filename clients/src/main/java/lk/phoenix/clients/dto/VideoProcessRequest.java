package lk.phoenix.clients.dto;

import java.io.Serializable;

public record VideoProcessRequest(int videoId, String url) implements Serializable {
}
