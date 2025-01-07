package lk.phoenix.videoProcessor.service;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Service
public class ColorExtract {
    public Map<String, Integer> extract(BufferedImage bi) {
        Map<String, Integer> colors = new HashMap();
        for (int y = 0; y < bi.getHeight(); y = y + 10) {
            for (int x = 0; x < bi.getWidth(); x = x + 10) {
                int p = bi.getRGB(x, y);

                int red = (p >> 16) & 0xff;
                int green = (p >> 8) & 0xff;
                int blue = (p) & 0xff;

                int avg = (red + green + blue) / 3;
                if (avg >= 20 || avg < 10) {
                    String colo = String.format("rgb(%s,%s,%s)", red, green, blue);
                    if (colors.get(colo) == null) {
                        colors.put(colo, 1);
                    } else {
                        int count = (Integer) colors.get(colo) + 1;
                        colors.replace(colo, count);
                    }

                }

            }
        }
        return colors;
    }
}
