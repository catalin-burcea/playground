package ro.cburcea.playground.barcodes;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.cburcea.playground.barcodes.generators.BarbecueBarcodeGenerator2;

import java.io.IOException;

@Controller
@RequestMapping("/barcodes")
public class BarcodesControllerMVC {

    @GetMapping(value = "/barbecue")
    public String barbecue(Model model) throws BarcodeException, IOException, OutputException {
        String upcaBarcode = BarbecueBarcodeGenerator2.generateUPCABarcodeBase64("12345678901");
        String ean13Barcode = BarbecueBarcodeGenerator2.generateEAN13BarcodeBase64("012345678901");
        String code128Barcode = BarbecueBarcodeGenerator2.generateCode128BarcodeBase64("Code128 Barcode using Barbecue");
        String pdf417Barcode = BarbecueBarcodeGenerator2.generatePDF417BarcodeBase64("PDF417 Barcode using Barbecue");

        model.addAttribute("barbecueUPCABarcode", upcaBarcode);
        model.addAttribute("barbecueEAN13Barcode", ean13Barcode);
        model.addAttribute("barbecueCode128Barcode", code128Barcode);
        model.addAttribute("barbecuePDF417Barcode", pdf417Barcode);

        return "index";
    }
}
