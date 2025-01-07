package lk.phoenix.videoProcessor.repository;

import lk.phoenix.videoProcessor.model.VideoProcessedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoProcessedDataRepository extends JpaRepository<VideoProcessedData,Long> {
}
