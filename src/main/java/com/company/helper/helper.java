package com.company.helper;

import com.company.entity.Developer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class helper {

//check that file is of excel type or not
public static boolean checkExcelFormat(MultipartFile file) {

    String contentType = file.getContentType();

    if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
        return true;
    } else {
        return false;
    }

}


    //convert excel to list of products
    public static List<Developer> convertExcelToListOfProduct(InputStream is) throws IOException {
        List<Developer> developerList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(is)) {
            // Check if workbook has any sheets
            if (workbook.getNumberOfSheets() == 0) {
                throw new IllegalArgumentException("Uploaded Excel file has no sheets.");
            }

            XSSFSheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new IllegalArgumentException("First sheet is missing in the uploaded Excel file.");
            }

            Iterator<Row> rows = sheet.iterator();

            // Skip header row if present
            if (rows.hasNext()) rows.next();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Developer dev = new Developer();

                dev.setId(getIntFromCell(currentRow.getCell(0)));
                dev.setFName(getStringFromCell(currentRow.getCell(1)));
                dev.setLName(getStringFromCell(currentRow.getCell(2)));
                dev.setAge(getIntFromCell(currentRow.getCell(3)));
                dev.setGender(getStringFromCell(currentRow.getCell(4)));
                dev.setCity(getStringFromCell(currentRow.getCell(5)));
                dev.setSalary(getLongFromCell(currentRow.getCell(6)));
                dev.setYOB(getIntFromCell(currentRow.getCell(7)));        //  YOB column fixed
                dev.setDeveloperId(getStringFromCell(currentRow.getCell(8))); // Developer ID column fixed

                developerList.add(dev);
            }
        }

        return developerList;
    }

    // --- Helpers ---
    private static String getStringFromCell(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double val = cell.getNumericCellValue();
                    if (val % 1 == 0) return String.valueOf((long) val);
                    return String.valueOf(val);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return "";
        }
    }

    private static int getIntFromCell(Cell cell) {
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        try {
            return Integer.parseInt(getStringFromCell(cell));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static long getLongFromCell(Cell cell) {
        if (cell == null) return 0L;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        }
        try {
            return Long.parseLong(getStringFromCell(cell));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }


}
