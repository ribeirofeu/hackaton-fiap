package com.hackaton.hackton.utils;

import com.hackaton.hackton.model.DailyRecord;
import com.hackaton.hackton.model.DailyReport;
import com.hackaton.hackton.model.Report;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelGenerator {

    private static final String INPUT = "Entrada ";
    private static final String OUTPUT = "Saída ";
    private static final String REPORT_NAME = "Relatório";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String HEADER_DAY = "Dia";
    private static final String HEADER_TIME = "Tempo";
    private static final String HEADER_NOTE = "Observação";
    private static final String TOTAL_PERIOD = "Período Total";
    private static final String INCORRECT_REGISTRATION = "Marcação Incorreta";

    public void generateExcel(Report report, String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(REPORT_NAME);

            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);

            int numberOfDays = 0;
            int rowNum = 1;

            List<Integer> rowsWithIncorrectRegistration = new ArrayList<>();
            Map<Integer, String> registerTotal = new HashMap<>();

            for (DailyReport dailyReport : report.getDailyReport()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dailyReport.getDay().toString());

                if (numberOfDays < dailyReport.getRecords().size()) {
                    numberOfDays = dailyReport.getRecords().size();
                }

                int cellNum = 1;
                for (DailyRecord record : dailyReport.getRecords()) {

                    Cell timeInCell = row.createCell(cellNum++);
                    timeInCell.setCellValue(convertLocalTimeToString(record.getTimeIn().toLocalTime()));

                    Cell timeOutCell = row.createCell(cellNum++);
                    if (record.getTimeOut() != null) {
                        timeOutCell.setCellValue(convertLocalTimeToString(record.getTimeOut().toLocalTime()));
                    } else {
                        rowsWithIncorrectRegistration.add(rowNum);
                        timeOutCell.setCellValue(" - ");
                        timeOutCell.setCellStyle(style);
                    }
                }

                registerTotal.put(rowNum, convertLocalTimeToString(dailyReport.getWorkTime()));
            }

            registerTotalWorkingTime(numberOfDays, registerTotal, sheet);

            setIncorrectRegistrationWarning(rowsWithIncorrectRegistration, sheet, numberOfDays);

            List<String> header = createHeader(sheet, numberOfDays);

            rowNum += 2;

            setTotalPeriod(report, sheet, rowNum);

            for (int i = 0; i < header.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                workbook.close();
            }
        }
    }

    private void setTotalPeriod(Report report, Sheet sheet, int rowNum) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(TOTAL_PERIOD);
        row.createCell(1).setCellValue(convertLocalTimeToString(report.getWorkPeriod()));
    }

    private List<String> createHeader(Sheet sheet, int numberOfDays) {
        Row headerRow = sheet.createRow(0);
        List<String> headers = generateHeader(numberOfDays);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
        }
        return headers;
    }

    private static void registerTotalWorkingTime(int numberOfDays, Map<Integer, String> registerTotal, Sheet sheet) {
        registerTotal.forEach((rowNumber, date) -> {
            Row row = sheet.getRow(rowNumber - 1);
            Cell workTimeCell = row.createCell((numberOfDays * 2) + 1);
            workTimeCell.setCellValue(date);
        });
    }

    private static void setIncorrectRegistrationWarning(List<Integer> rowsWithIncorrectRegistration, Sheet sheet, int finalNumberOfDays) {
        if (!rowsWithIncorrectRegistration.isEmpty()) {
            for (int value : rowsWithIncorrectRegistration) {
                Row row = sheet.getRow(value - 1);
                Cell messageCell = row.createCell((finalNumberOfDays * 2) + 2);
                messageCell.setCellValue(INCORRECT_REGISTRATION);
            }
        }
    }

    private List<String> generateHeader(int numberOfDays) {
        List<String> header = new ArrayList<>();
        header.add(HEADER_DAY);

        for (int i = 0; i < numberOfDays; i++) {
            int day = i + 1;
            header.add(INPUT + day);
            header.add(OUTPUT + day);
        }

        header.add(HEADER_TIME);
        header.add(HEADER_NOTE);
        return header;
    }

    private String convertLocalTimeToString(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern(TIME_PATTERN));
    }
}
