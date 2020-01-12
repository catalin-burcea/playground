package ro.cburcea.playground.barcodes.generators;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class Barcode4jBarcodeGenerator2 {

//    public static byte[] generateUPCABarcodeToByteArray(String barcodeText) throws IOException {
//        UPCABean barcodeGenerator = new UPCABean();
//        FileOutputStream out = new FileOutputStream("barcodes/src/main/resources/barcode4j-upca.png");
//
//        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", 160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
//        barcodeGenerator.generateBarcode(canvas, barcodeText);
//        canvas.finish();
//        return Utils.convertImageToBase64(canvas.getBufferedImage()).toByteArray();
//    }
    
    public static void generateUPCABarcode(String barcodeText) throws IOException {
        UPCABean barcodeGenerator = new UPCABean();
        FileOutputStream out = new FileOutputStream("barcodes/src/main/resources/barcode4j-upca.png");

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", 160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        canvas.finish();
    }

    public static void generateCode128Barcode(String barcodeText) throws IOException {
        Code128Bean barcodeGenerator = new Code128Bean();
        FileOutputStream out = new FileOutputStream("barcodes/src/main/resources/barcode4j-code128.png");

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", 160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        canvas.finish();
    }

    public static void generateEAN13Barcode(String barcodeText) throws IOException {
        EAN13Bean barcodeGenerator = new EAN13Bean();
        FileOutputStream out = new FileOutputStream("barcodes/src/main/resources/barcode4j-ean13.png");

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", 160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        canvas.finish();
    }
    
    public static void generatePDF147Barcode(String barcodeText) throws IOException {
        PDF417Bean barcodeGenerator = new PDF417Bean();
        FileOutputStream out = new FileOutputStream("barcodes/src/main/resources/barcode4j-pdf417.png");

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", 160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        canvas.finish();
    }

    public static void generateCode128BarcodeSO(String barcodeText) throws IOException {
        Code128Bean barcodeGenerator = new Code128Bean();
//        barcodeGenerator.setBarHeight(344);
        barcodeGenerator.setModuleWidth(5);
        FileOutputStream out = new FileOutputStream("barcodes/src/main/resources/barcode4j-code128-so2.png");

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", 160, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        canvas.finish();
    }

    public static void main(String[] args) throws IOException {
//        generateUPCABarcode("123456789111");
//        generateEAN13Barcode("1234567891118");
//        generateCode128Barcode("My Code128 Barcode using Barcode4j library");
//        generatePDF147Barcode("My PDF417 Barcode using Barcode4j library");
        generateCode128BarcodeSO("My Code128 Barcode using Barcode4j library");

    }

}
