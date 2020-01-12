package ro.cburcea.playground.barcodes.generators;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Utils {

    static String convertImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bao);
        return Base64.getEncoder().encodeToString(bao.toByteArray());
    }

    static byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bao);
        return bao.toByteArray();
    }
}
