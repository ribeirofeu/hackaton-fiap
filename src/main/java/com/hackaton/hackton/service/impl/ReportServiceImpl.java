package com.hackaton.hackton.service.impl;

import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.service.ClockRegisterService;
import com.hackaton.hackton.service.ReportService;
import com.hackaton.hackton.utils.ExcelGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import com.hackaton.hackton.utils.ResendEmail;
import com.hackaton.hackton.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
@Log4j2
public class ReportServiceImpl implements ReportService {

  @Autowired
  private ClockRegisterService clockRegisterService;

  @Autowired
  private ResendEmail resendEmail;

  @Override
  public void generateReport(UUID userId, int month, int year) {

      String file = "relatorio.xlsx";

    try {
      // TODO: Find USER By USER ID
      String email = "ribeiro.feu@gmail.com";

      LocalDate startDate = YearMonth.of(year, month).atDay(1);
      LocalDate finalDate = YearMonth.of(year, month).atEndOfMonth();

      log.info("Generate Report - startDate {} - finalDate {}", startDate, finalDate);

      Report monthReport = clockRegisterService.getRegisters(startDate, finalDate, userId);

      ExcelGenerator excelGenerator = new ExcelGenerator();

      excelGenerator.generateExcel(monthReport, file);
      resendEmail.sendEmail(file, "relatorio_mensal.xlsx", email);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }finally{
        Utils.deleteIfExists(file);
    }
  }
}
