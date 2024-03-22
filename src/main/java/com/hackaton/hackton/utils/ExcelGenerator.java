package com.hackaton.hackton.utils;

import com.hackaton.hackton.model.DailyRecord;
import com.hackaton.hackton.model.DailyReport;
import com.hackaton.hackton.model.Report;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelGenerator {

  public void generateExcel(Report report, String filePath) throws IOException {
    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Relatório");

      // Cabeçalhos
      Row headerRow = sheet.createRow(0);
      int lastRow =0;
      String[] headers = {"Day", "Entrada 1", "Saida 1", "Entrada 2", "Saida 2", "Entrada 3", "Saida 3","Tempo", "Observação"};
      for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
      }

      int rowNum = 1;
      for (DailyReport dailyReport : report.getDailyReport()) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(dailyReport.getDay().toString());

        int cellNum = 0;
        for (DailyRecord record : dailyReport.getRecords()) {
          cellNum = 1;

          Cell timeInCell = row.createCell(cellNum++);
          timeInCell.setCellValue(
              record.getTimeIn().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

          Cell timeOutCell = row.createCell(cellNum++);
          if (record.getTimeOut() != null) {
            timeOutCell.setCellValue(
                record.getTimeOut().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
          }else {
              Cell messageCell = row.createCell(8);
              messageCell.setCellValue("Marcação Incorreta");
          }
        }

        Cell workTimeCell = row.createCell(7);
        workTimeCell.setCellValue(
            dailyReport.getWorkTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
      }

        rowNum += 2;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Total Periodo");
        row.createCell(1).setCellValue(report.getWorkPeriod().format(DateTimeFormatter.ofPattern("HH:mm:ss")));



      // Auto dimensiona as colunas
      for (int i = 0; i < headers.length; i++) {
        sheet.autoSizeColumn(i);
      }

      // Escreve no arquivo
      try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
        workbook.write(fileOut);
        workbook.close();
      }
    }
  }
}
