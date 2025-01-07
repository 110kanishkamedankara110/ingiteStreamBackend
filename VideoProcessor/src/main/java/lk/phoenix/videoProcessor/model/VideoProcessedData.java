package lk.phoenix.videoProcessor.model;

import jakarta.persistence.*;

import java.io.Serializable;
@Table(name = "video_processed_data")
@Entity
public class VideoProcessedData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "video_id")
    private int videoId;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "primary_color")
    private String primaryColor;
    @Column(name = "secondary_color")
    private String secondaryColor;
    @Column(name = "color_pallet",length = 1000000)
    private String colorPallet;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public String getColorPallet() {
        return colorPallet;
    }

    public void setColorPallet(String colorPallet) {
        this.colorPallet = colorPallet;
    }
}
