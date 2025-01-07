package lk.phoenix.videoProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "lk.phoenix.rabbitMqMessaging",
                "lk.phoenix.videoProcessor"
        }
)
@EnableFeignClients(
        basePackages = "lk.phoenix.clients"
)
public class VideoProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoProcessorApplication.class, args);
    }
}
