package lk.phoenix.clients.dto;


import java.io.Serializable;

public record UploadResponse(int status, String message, String location) implements Serializable {

}
