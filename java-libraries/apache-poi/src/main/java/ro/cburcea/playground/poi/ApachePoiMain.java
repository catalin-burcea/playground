package ro.cburcea.playground.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ApachePoiMain {
    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);

        Sheet nonIdenticalSheet = createSheet(workbook);
        createHeaderRow1(workbook, nonIdenticalSheet, boldFont);
        createHeaderRow2(workbook, nonIdenticalSheet, boldFont);
        createHeaderRow3(workbook, nonIdenticalSheet, boldFont);
        setBordersToMergedCells(nonIdenticalSheet);

        writeExcel(workbook);
    }

    private static Sheet createSheet(XSSFWorkbook workbook) {
        Sheet sheet = workbook.createSheet("Non-Identical");
        for (int i = 0; i < 30; i++) {
            sheet.setColumnWidth(3 + i, 6000);
        }
        CellRangeAddress region1 = new CellRangeAddress(1, 2, 1, 3);
        CellRangeAddress region2 = new CellRangeAddress(1, 1, 4, 26);
        sheet.addMergedRegion(region1);
        sheet.addMergedRegion(region2);
        return sheet;
    }

    private static void setBordersToMergedCells(Sheet sheet) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress rangeAddress : mergedRegions) {
            RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
        }
    }

    private static void writeExcel(XSSFWorkbook workbook) throws IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();

        System.out.println("Finished");
    }

    private static void createHeaderRow1(XSSFWorkbook workbook, Sheet sheet, XSSFFont boldFont) {
        Row rowHeader1 = sheet.createRow(1);

        CellStyle headerStyle1 = workbook.createCellStyle();

        headerStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle1.setAlignment(HorizontalAlignment.CENTER);

        XSSFFont messageTypeFont = workbook.createFont();
        messageTypeFont.setFontName("Arial");
        messageTypeFont.setFontHeightInPoints((short) 16);
        messageTypeFont.setBold(true);
        headerStyle1.setFont(messageTypeFont);

        Cell headerCell1 = rowHeader1.createCell(1);
        headerCell1.setCellValue("MT103");
        headerCell1.setCellStyle(headerStyle1);


        CellStyle headerStyle2 = workbook.createCellStyle();
        headerStyle2.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headerStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle2.setAlignment(HorizontalAlignment.CENTER);
        headerStyle2.setFont(boldFont);

        Cell headerCell2 = rowHeader1.createCell(4);
        headerCell2.setCellValue("Swift MT Tags");
        headerCell2.setCellStyle(headerStyle2);
    }

    private static CellStyle getCellStyleWithBorder(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();

        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        return style;
    }

    private static void createHeaderRow2(XSSFWorkbook workbook, Sheet sheet, XSSFFont boldFont) {
        Row rowHeader2 = sheet.createRow(2);
        CellStyle tagHeaderStyle = getCellStyleWithBorder(workbook);
        tagHeaderStyle.setFont(boldFont);
        tagHeaderStyle.setAlignment(HorizontalAlignment.CENTER);

        List<String> tagNames = Arrays.asList("20", "13C", "23B", "23E", "26T", "32A", "33B", "36", "50a", "51A",
                "52a", "53a", "54a", "55a", "56a", "57a", "59a", "70", "71A", "71F", "71G", "72", "77B");

        for (int i = 0; i < tagNames.size(); i++) {
            String tagName = tagNames.get(i);
            Cell cell = rowHeader2.createCell(4 + i);
            cell.setCellValue(tagName);
            cell.setCellStyle(tagHeaderStyle);
        }

    }

    private static void createHeaderRow3(XSSFWorkbook workbook, Sheet sheet, XSSFFont boldFont) {
        Row rowHeader3 = sheet.createRow(3);
        CellStyle tagHeaderStyle = getCellStyleWithBorder(workbook);
        tagHeaderStyle.setFont(boldFont);
        tagHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
        tagHeaderStyle.setVerticalAlignment(VerticalAlignment.TOP);
        tagHeaderStyle.setWrapText(true);

        CellStyle tagHeaderStyle2 = getCellStyleWithBorder(workbook);
        tagHeaderStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        tagHeaderStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        tagHeaderStyle2.setAlignment(HorizontalAlignment.CENTER);
        tagHeaderStyle.setVerticalAlignment(VerticalAlignment.TOP);
        tagHeaderStyle2.setFont(boldFont);
        tagHeaderStyle2.setWrapText(true);

        List<String> tagNames = Arrays.asList("Sender's Reference", "Time Indication", "Bank Operation Code", "Instruction Code",
                "Transaction Type Code", "Value Date / Currency / Interbank Settled Amount", "Currency / Instructed Amount", "Exchange Rate",
                "Ordering Customer", "Sending Institution", "Ordering Institution", "Sender's Correspondent", "Receiver's Correspondent",
                "Third Reimbursement Institution", "Intermediary Institution", "Account With Institution", "Beneficiary Customer",
                "Remittance Information", "Details of Charges", "Sender's Charges", "Receiver's Charges",
                "Sender to Receiver Information", "Regulatory Reporting");


        Cell cell1 = rowHeader3.createCell(1);
        cell1.setCellValue("Platform");
        cell1.setCellStyle(tagHeaderStyle2);

        Cell cell2 = rowHeader3.createCell(2);
        cell2.setCellValue("Swift Id");
        cell2.setCellStyle(tagHeaderStyle2);

        Cell cell3 = rowHeader3.createCell(3);
        cell3.setCellValue("Discrepancy Count");
        cell3.setCellStyle(tagHeaderStyle2);

        for (int i = 0; i < tagNames.size(); i++) {
            String tagName = tagNames.get(i);
            Cell cell = rowHeader3.createCell(4 + i);
            cell.setCellValue(tagName);
            cell.setCellStyle(tagHeaderStyle);
        }

    }
}
