package ro.cburcea.playground.barcodes;

import com.google.zxing.WriterException;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cburcea.playground.barcodes.generators.BarbecueBarcodeGenerator;
import ro.cburcea.playground.barcodes.generators.Barcode4jBarcodeGenerator;
import ro.cburcea.playground.barcodes.generators.QRGenBarcodeGenerator;
import ro.cburcea.playground.barcodes.generators.ZxingBarcodeGenerator;

import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/barcodes")
public class BarcodesController {

    //...
    @ExceptionHandler({ BarcodeException.class, OutputException.class })
    public void handleException() {
        //
    }

    //Barbecue library

    @GetMapping(value = "/barbecue/upca", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barbecueUPCABarcode(@RequestParam("barcodeText") String barcodeText) throws BarcodeException, OutputException {
        BufferedImage image = BarbecueBarcodeGenerator.generateUPCABarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/barbecue/ean13", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barbecueEAN13Barcode(@RequestParam("barcodeText") String barcodeText) throws BarcodeException, OutputException {
        BufferedImage image = BarbecueBarcodeGenerator.generateEAN13BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/barbecue/code128", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barbecueCode128Barcode(@RequestParam("barcodeText") String barcodeText) throws BarcodeException, OutputException {
        BufferedImage image = BarbecueBarcodeGenerator.generateCode128BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/barbecue/pdf417", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barbecuePDF417Barcode(@RequestParam("barcodeText") String barcodeText) throws BarcodeException, OutputException {
        BufferedImage image = BarbecueBarcodeGenerator.generatePDF417BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    //Barcode4j library

    @GetMapping(value = "/barcode4j/upca", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barcode4jUPCABarcode(@RequestParam("barcodeText") String barcodeText) {
        BufferedImage image = Barcode4jBarcodeGenerator.generateUPCABarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/barcode4j/ean13", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barcode4jEAN13Barcode(@RequestParam("barcodeText") String barcodeText) {
        BufferedImage image = Barcode4jBarcodeGenerator.generateEAN13BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/barcode4j/code128", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barcode4jCode128Barcode(@RequestParam("barcodeText") String barcodeText) {
        BufferedImage image = Barcode4jBarcodeGenerator.generateCode128BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/barcode4j/pdf417", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barcode4jPDF417Barcode(@RequestParam("barcodeText") String barcodeText) {
        BufferedImage image = Barcode4jBarcodeGenerator.generatePDF417BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    //Zxing library

    @GetMapping(value = "/zxing/upca", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingUPCABarcode(@RequestParam("barcodeText") String barcodeText) throws WriterException {
        BufferedImage image = ZxingBarcodeGenerator.generateUPCABarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/zxing/ean13", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingEAN13Barcode(@RequestParam("barcodeText") String barcodeText) throws WriterException {
        BufferedImage image = ZxingBarcodeGenerator.generateEAN13BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/zxing/code128", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingCode128Barcode(@RequestParam("barcodeText") String barcodeText) throws WriterException {
        BufferedImage image = ZxingBarcodeGenerator.generateCode128BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/zxing/pdf417", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingPDF417Barcode(@RequestParam("barcodeText") String barcodeText) throws WriterException {
        BufferedImage image = ZxingBarcodeGenerator.generatePDF417BarcodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/zxing/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> zxingQRCode(@RequestParam("barcodeText") String barcodeText) throws WriterException {
        BufferedImage image = ZxingBarcodeGenerator.generateQRCodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    //QRGen

    @GetMapping(value = "/qrgen/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> qrgenQRCode(@RequestParam("barcodeText") String barcodeText) throws IOException {
        BufferedImage image = QRGenBarcodeGenerator.generateQRCodeImage(barcodeText);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
