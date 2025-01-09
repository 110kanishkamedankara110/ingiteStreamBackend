package lk.phoenix.videoProcessor.repository;

import lk.phoenix.videoProcessor.model.VideoProcessedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoProcessedDataRepository extends JpaRepository<VideoProcessedData,Long> {
    @Query("SELECT v FROM VideoProcessedData v WHERE v.videoId=:videoId")
    public VideoProcessedData findByVideoId(int videoId);
}
