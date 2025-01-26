package lk.phoenix.videoProcessor.service;

import com.google.gson.Gson;
import lk.phoenix.clients.Exception.ServiceUnavailableException;
import lk.phoenix.clients.dto.UploadResponse;
import lk.phoenix.clients.dto.VideoProcessRequest;
import lk.phoenix.clients.dto.VideoProcessorResponce;
import lk.phoenix.clients.uploaderService.UploaderService;
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
import java.io.*;
import java.util.Comparator;
import java.util.Map;

@Service
public class Processor {

    public Processor(ColorExtract colorExtract, VideoProcessedDataRepository videoProcessedDataRepository, UploaderService uploader) {
        this.videoProcessedDataRepository = videoProcessedDataRepository;
        this.uploader = uploader;
        this.colorExtract = colorExtract;
    }

    VideoProcessedDataRepository videoProcessedDataRepository;
    UploaderService uploader;
    ColorExtract colorExtract;

    public VideoProcessorResponce process(VideoProcessRequest videoProcessRequest) throws IOException {
        UploadResponse response = new UploadResponse(0, "", "");

        String thumbnail = videoProcessRequest.thumbnail();

        MultipartFile multipartFile = null;
        if (thumbnail == null) {

            File f = null;
            try {
                f = uploader.fetchVideo(videoProcessRequest.url()).getBody().getFile();
            } catch (ServiceUnavailableException e) {
                return new VideoProcessorResponce(videoProcessRequest.videoId(), "Fetch Service Unavailable Please Try again Later", 503);
            }

            byte[] byteArray = extractImages(f);
            String name = f.getName() + ".png";
            multipartFile = new CustomMultipart(byteArray, name);
            try {
                response = uploader.upload(multipartFile);
            } catch (ServiceUnavailableException e) {
                return new VideoProcessorResponce(videoProcessRequest.videoId(), "Uploader Service Unavailable Please Try again Later", 503);
            }
        } else {
            try {
                File file = uploader.fetchImage(thumbnail).getBody().getFile();
                multipartFile = convertFileToMultipartFile(file);
            } catch (ServiceUnavailableException e) {
                return new VideoProcessorResponce(videoProcessRequest.videoId(), "Fetch Service Unavailable Please Try again Later", 503);
            }
        }
        if (response.status() == 200 || thumbnail != null) {
            Map<String, Integer> colors = colorExtract.extract(convertMultipartFileToBufferedImage(multipartFile));
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
            vp.setImageUrl(response.location().isEmpty() ? thumbnail : response.location());
            vp.setVideoId(videoProcessRequest.videoId());

            videoProcessedDataRepository.saveAndFlush(vp);

            return new VideoProcessorResponce(videoProcessRequest.videoId(), "success", 200);
        } else {
            return new VideoProcessorResponce(videoProcessRequest.videoId(), response.message(), response.status());
        }
    }

    public static MultipartFile convertFileToMultipartFile(File file) throws IOException {
        byte[] content = new byte[(int) file.length()];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(content);
        }

        return new CustomMultipart(content, file.getName());
    }

    public byte[] extractImages(File f) throws IOException {
        if (f.exists()) {
            FFmpegFrameGrabber g = new FFmpegFrameGrabber(f.getAbsolutePath());
            g.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            int j = g.getLengthInVideoFrames();

            for (int i = 0; i < j; i++) {
                int black = 0;
                int pix = 0;
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
                    g.stop();
                    return byteArrayOutputStream.toByteArray();
                }
                if (i == j - 1) {
                    j *= 2;
                }

            }
            g.stop();
        }
        return null;
    }

    public static BufferedImage convertMultipartFileToBufferedImage(MultipartFile file) throws IOException {
        // Get InputStream from MultipartFile
        try (InputStream inputStream = file.getInputStream()) {
            // Read the image from InputStream
            return ImageIO.read(inputStream);
        }
    }

}
