package lk.phoenix.videoStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "lk.phoenix.rabbitMqMessaging",
                "lk.phoenix.videoStream"
        }
)
@EnableFeignClients(
        basePackages = "lk.phoenix.clients"
)
public class VideoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideoServiceApplication.class, args);
    }

}
