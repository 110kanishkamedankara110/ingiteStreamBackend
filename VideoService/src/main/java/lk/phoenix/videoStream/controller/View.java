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
public class View {

    UploaderService uploader;

    public View(UploaderService uploader) {
        this.uploader = uploader;
    }


    @GetMapping("/view")
    public ResponseEntity<Resource> play(@RequestParam("i") String location) throws ServiceUnavailableException {
        return uploader.fetchImage(location);
    }
}

