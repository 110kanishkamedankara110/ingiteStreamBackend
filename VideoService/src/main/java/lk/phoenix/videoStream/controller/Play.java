package lk.phoenix.videoStream.controller;

import lk.phoenix.clients.Exception.ServiceUnavailableException;
import lk.phoenix.clients.uploaderService.UploaderService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/VideoService")
public class Play {

    UploaderService uploader;

    public Play(UploaderService uploader) {
        this.uploader = uploader;
    }

    @GetMapping("/play")
    public ResponseEntity<Resource> play(@RequestParam("v") String location) throws ServiceUnavailableException {
        return uploader.fetchVideo(location);
    }

}
