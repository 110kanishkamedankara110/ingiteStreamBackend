package lk.phoenix.videoProcessor.service;

import com.google.gson.Gson;
import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.dto.VideoProcessRequest;
import lk.phoenix.clients.dto.VideoProcessorResponce;
import lk.phoenix.clients.uploaderService.Uploader;
import lk.phoenix.videoProcessor.model.VideoProcessedData;
import lk.phoenix.videoProcessor.repository.VideoProcessedDataRepository;
import lk.phoenix.videoProcessor.util.CustomMultipart;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

@Service
public class Processor {

    public Processor(ColorExtract colorExtract, VideoProcessedDataRepository videoProcessedDataRepository, Uploader uploader) {
        this.videoProcessedDataRepository = videoProcessedDataRepository;
        this.uploader = uploader;
        this.colorExtract = colorExtract;
    }

    VideoProcessedDataRepository videoProcessedDataRepository;
    Uploader uploader;
    ColorExtract colorExtract;

    public VideoProcessorResponce process(VideoProcessRequest videoProcessRequest) throws IOException {
        File f = new File(videoProcessRequest.url());
        if (f.exists()) {
            FFmpegFrameGrabber g = new FFmpegFrameGrabber(f.getAbsolutePath());
            g.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            int j = 50;
            int black = 0;
            int pix = 0;
            for (int i = 0; i < j; i++) {
                Frame frame = g.grabImage();
                BufferedImage bi = converter.convert(frame);
                for (int y = 0; y < bi.getHeight(); y = y + 5) {
                    for (int x = 0; x < bi.getWidth(); x = x + 5) {
                        int p = bi.getRGB(x, y);

                        int red = (p >> 16) & 0xff;
                        int green = (p >> 8) & 0xff;
                        int blue = (p) & 0xff;

                        int avg = (red + green + blue) / 3;

                        pix++;
                        if (avg <= 20) {
                            black++;
                        }


                    }
                }
                if (black >= ((pix / 3) * 2)) {

                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(bi, "png", byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    MultipartFile multipartFile = new CustomMultipart(byteArray, f.getName() + System.currentTimeMillis()+".png");
                    UploadResponse response = uploader.upload(multipartFile);

                    if (response.status() == 200) {
                        Map<String, Integer> colors = colorExtract.extract(bi);
                        String primaryColor = colors.entrySet()
                                .stream()
                                .max(Comparator.comparingInt(Map.Entry::getValue))
                                .map(Map.Entry::getKey)
                                .orElse(null);

                        String secondaryColor = colors.entrySet()
                                .stream()
                                .min(Comparator.comparingInt(Map.Entry::getValue))
                                .map(Map.Entry::getKey)
                                .orElse(null);

                        String colorPallet = new Gson().toJson(colors);

                        VideoProcessedData vp = new VideoProcessedData();
                        vp.setColorPallet(colorPallet);
                        vp.setSecondaryColor(secondaryColor);
                        vp.setPrimaryColor(primaryColor);
                        vp.setImageUrl(response.location());
                        vp.setVideoId(videoProcessRequest.videoId());

                        videoProcessedDataRepository.saveAndFlush(vp);
                        g.stop();

                        return new VideoProcessorResponce(videoProcessRequest.videoId(),"success",200);
                    }else{
                        g.stop();
                        return new VideoProcessorResponce(videoProcessRequest.videoId(), response.message(), response.status());
                    }
                }
                if (i == j - 1) {
                    j *= 2;
                }

            }

            g.stop();
        }
        return null;
    }

}
