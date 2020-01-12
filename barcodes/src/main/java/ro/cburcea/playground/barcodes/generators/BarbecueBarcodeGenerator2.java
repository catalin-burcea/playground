package ro.cburcea.playground.barcodes.generators;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarbecueBarcodeGenerator2 {

    public static String generateUPCABarcodeBase64(String barcodeText) throws BarcodeException, OutputException, IOException {
        Barcode barcode = BarcodeFactory.createUPCA(barcodeText); //checksum is automatically added
        barcode.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        BufferedImage image = BarcodeImageHandler.getImage(barcode);

        return Utils.convertImageToBase64(image);
    }

    public static void generateUPCABarcodeToFile(String barcodeText) throws BarcodeException, OutputException {
        Barcode barcode = BarcodeFactory.createUPCA(barcodeText); //checksum is automatically added
        File outputFile = new File("barcodes/src/main/resources/barbecue-upca.png");
        BarcodeImageHandler.savePNG(barcode, outputFile);
    }

    //EAN13

    public static String generateEAN13BarcodeBase64(String barcodeText) throws BarcodeException, OutputException, IOException {
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText); //checksum is automatically added
        barcode.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        BufferedImage image = BarcodeImageHandler.getImage(barcode);

        return Utils.convertImageToBase64(image);
    }

    public static void generateEAN13BarcodeToFile(String barcodeText) throws BarcodeException, OutputException {
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
        File outputFile = new File("barcodes/src/main/resources/barbecue-ean13.png");
        BarcodeImageHandler.savePNG(barcode, outputFile);
    }

    // Code128

    public static String generateCode128BarcodeBase64(String barcodeText) throws BarcodeException, OutputException, IOException {
        Barcode barcode = BarcodeFactory.createCode128(barcodeText);
        barcode.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        BufferedImage image = BarcodeImageHandler.getImage(barcode);

        return Utils.convertImageToBase64(image);
    }


    public static void generateCode128BarcodeToFile(String barcodeText) throws BarcodeException, OutputException {
        Barcode barcode = BarcodeFactory.createCode128(barcodeText);
//        barcode.set
        File outputFile = new File("barcodes/src/main/resources/barbecue-code128.png");
        BarcodeImageHandler.savePNG(barcode, outputFile);
    }

    //PDF417

    public static String generatePDF417BarcodeBase64(String barcodeText) throws BarcodeException, OutputException, IOException {
        Barcode barcode = BarcodeFactory.createPDF417(barcodeText);
        barcode.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        BufferedImage image = BarcodeImageHandler.getImage(barcode);

        return Utils.convertImageToBase64(image);
    }

    public static void generatePDF417BarcodeToFile(String barcodeText) throws BarcodeException, OutputException {
        Barcode barcode = BarcodeFactory.createPDF417(barcodeText);
        File outputFile = new File("barcodes/src/main/resources/barbecue-pdf417.png");
        BarcodeImageHandler.savePNG(barcode, outputFile);
    }

    public static void main(String[] args) throws BarcodeException, OutputException, IOException {
        generateUPCABarcodeToFile("12345678901");
        generateEAN13BarcodeToFile("012345678901");
        generateCode128BarcodeToFile("My Code128 Barcode using Barbecue library");
        generatePDF417BarcodeToFile("My PDF417 Barcode using Barbecue library");
    }

}
