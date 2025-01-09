package lk.phoenix.clients.dto;

import java.io.Serializable;


public record VideoProcessedData(String colorPallet,String secondaryColor,String primaryColor,Long id,int videoId,String imageUrl) {
}
