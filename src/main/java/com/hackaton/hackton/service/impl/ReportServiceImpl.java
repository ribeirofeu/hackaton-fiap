package com.hackaton.hackton.service.impl;

import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.service.ClockRegisterService;
import com.hackaton.hackton.service.ReportService;
import com.hackaton.hackton.utils.ExcelGenerator;
import com.hackaton.hackton.utils.ResendEmail;
import com.hackaton.hackton.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

@Service
@Log4j2
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ClockRegisterService clockRegisterService;

    @Autowired
    private ResendEmail resendEmail;

    private static final String FILE = "relatorio.xlsx";
    private static final String ATTACHED_FILE = "relatorio_mensal.xlsx";

    @Override
    public void generateReport(UUID userId, String email, int month, int year) {
        try {
            LocalDate startDate = YearMonth.of(year, month).atDay(1);
            LocalDate finalDate = YearMonth.of(year, month).atEndOfMonth();

            log.info("Generate Report - startDate {} - finalDate {}", startDate, finalDate);

            Report monthReport = clockRegisterService.getRegisters(startDate, finalDate, userId);

            ExcelGenerator excelGenerator = new ExcelGenerator();

            excelGenerator.generateExcel(monthReport, FILE);
            resendEmail.sendEmail(FILE, ATTACHED_FILE, email);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Utils.deleteIfExists(FILE);
        }
    }
}
