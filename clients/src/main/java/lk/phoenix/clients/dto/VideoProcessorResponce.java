package lk.phoenix.clients.dto;

import java.io.Serializable;

public record VideoProcessorResponce(int videoId, String message, int status) implements Serializable {
}
