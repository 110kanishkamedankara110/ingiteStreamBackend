package lk.phoenix.videoStream.repository;

import lk.phoenix.videoStream.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("UPDATE Video v SET v.status=:status WHERE v.id=:id")
    @Modifying
    @Transactional
    void updateStatusById(Long id,String status);
    @Query("SELECT v FROM Video v WHERE v.status='Processed'")
    List<Video> getAll();
}
