package com.automation.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Excel test data into plain lists. Uses {@link DataFormatter} so every
 * cell comes back as the String a user would see in Excel, whatever its type.
 */
public final class ExcelReader {

    private static final DataFormatter FORMATTER = new DataFormatter();

    private ExcelReader() {
    }

    public static List<List<String>> readSheet(String filePath, String sheetName) {
        List<List<String>> rows = new ArrayList<>();
        try (FileInputStream input = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(input)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in " + filePath);
            }
            int columns = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();
            for (Row row : sheet) {
                List<String> values = new ArrayList<>();
                for (int i = 0; i < columns; i++) {
                    values.add(row.getCell(i) == null ? "" : FORMATTER.formatCellValue(row.getCell(i)).trim());
                }
                rows.add(values);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read Excel file: " + filePath, e);
        }
        return rows;
    }
}
