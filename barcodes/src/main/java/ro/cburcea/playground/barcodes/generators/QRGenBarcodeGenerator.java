package ro.cburcea.playground.barcodes.generators;

import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRGenBarcodeGenerator {

    public static BufferedImage generateQRCodeImage(String barcodeText) throws IOException {
        File output = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .file();
        return ImageIO.read(output);
    }
}
