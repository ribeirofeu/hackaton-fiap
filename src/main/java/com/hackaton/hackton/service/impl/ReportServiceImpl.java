package com.hackaton.hackton.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hackaton.hackton.model.DailyRecord;
import com.hackaton.hackton.model.DailyReport;
import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.model.entity.RegisterEntity;
import com.hackaton.hackton.repository.RegisterRepository;
import com.hackaton.hackton.service.ClockRegisterService;
import com.hackaton.hackton.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

  @Autowired
  ClockRegisterService clockRegisterService;

  @Override
  public void generateReport(UUID userId, Long month, Long year) {
    // TODO: Find USER By USER ID
    String email = "mock@gmail.com";

    LocalDate currentDate = LocalDate.now();

    // Get the first day of the previous month
    LocalDate firstDayOfLastMonth = currentDate.minusMonths(1).withDayOfMonth(1);

    // Get the last day of the previous month
    LocalDate lastDayOfLastMonth = currentDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

    Report lastMonthReport = clockRegisterService.getRegisters(firstDayOfLastMonth, lastDayOfLastMonth, userId);

    try {
      var mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      String stringReport = mapper.writeValueAsString(lastMonthReport);
      System.out.print(stringReport);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
