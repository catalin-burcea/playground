package ro.cburcea.playground.barcodes.generators;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZxingBarcodeGenerator2 {

    public static void generateUPCABarcode(String barcodeText, int width, int height) throws WriterException, IOException {
        UPCAWriter barcodeWriter = new UPCAWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.UPC_A, width, height);

        Path path = Paths.get("barcodes/src/main/resources/zxing-upca.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generateEAN13Barcode(String barcodeText, int width, int height) throws WriterException, IOException {
        EAN13Writer barcodeWriter = new EAN13Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, width, height);

        Path path = Paths.get("barcodes/src/main/resources/zxing-ean13.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generateCode128Barcode(String barcodeText, int width, int height) throws WriterException, IOException {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height);

        Path path = Paths.get("barcodes/src/main/resources/zxing-code128.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generatePDF417Barcode(String barcodeText, int width, int height) throws WriterException, IOException {
        PDF417Writer barcodeWriter = new PDF417Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.PDF_417, width, height);

        Path path = Paths.get("barcodes/src/main/resources/zxing-pdf417.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generateQRCode(String barcodeText, int width, int height) throws WriterException, IOException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, width, height);

        Path path = Paths.get("barcodes/src/main/resources/zxing-qrcode.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generateCode128BarcodeSO(String barcodeText, int width, int height) throws WriterException, IOException {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height);

        Path path = Paths.get("barcodes/src/main/resources/zxing-code128-so.png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void main(String[] args) throws WriterException, IOException {
//        generateUPCABarcode("123456789111", 400, 150);
//        generateEAN13Barcode("1234567891118", 300, 150); // checksum!!!!!!
//        generateCode128Barcode("My Code128 barcode using Zxing library", 350, 100);
//        generatePDF417Barcode("My PDF417 barcode using Zxing library", 350, 100);
//        generateQRCode("My QR Code using Zxing library", 400, 400);

        generateCode128BarcodeSO("My Code128 barcode using Zxing library", 350, 100);
    }
}