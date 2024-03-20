package com.hackaton.hackton.service.impl;

import com.hackaton.hackton.model.DailyRecord;
import com.hackaton.hackton.model.DailyReport;
import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.model.entity.RegisterEntity;
import com.hackaton.hackton.repository.RegisterRepository;
import com.hackaton.hackton.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

  @Autowired
  private RegisterRepository registerRepository;

  @Override
  public Report genereteReport(LocalDate startDate, LocalDate finalDate, UUID userID) {

    List<RegisterEntity> result =
        registerRepository.findByUserIDAndRegisterTimeBetweenOrderByRegisterTimeAsc(
            userID, startDate.atTime(LocalTime.MIN), finalDate.atTime(LocalTime.MAX));

    List<LocalDate> dateList =
        result.stream().map(entity -> entity.getRegisterTime().toLocalDate()).toList();

    Set<LocalDate> dates = new HashSet<>(dateList);

    Report report = new Report(startDate, finalDate, userID);

    dates.forEach(
        date -> {
          DailyReport dailyReport1 = generateDailyReport(date, result);
          report.addDaily(dailyReport1);
        });
    return report;
  }

  private DailyReport generateDailyReport(LocalDate date, List<RegisterEntity> result) {

    DailyReport dailyReport = new DailyReport(date);
    List<RegisterEntity> filterList =
        new ArrayList<>(
            result.stream()
                .filter(entity -> date.isEqual(entity.getRegisterTime().toLocalDate()))
                .toList());

    while (!filterList.isEmpty()) {
      DailyRecord dailyRecord = generateDailyRecord(filterList);
      dailyReport.addRecord(dailyRecord);
    }
    return dailyReport;
  }


  private DailyRecord generateDailyRecord(List<RegisterEntity> list) {
    LocalDateTime timeIn = null;
    try {
      timeIn = list.remove(0).getRegisterTime();
      LocalDateTime timeOut = list.remove(0).getRegisterTime();
      return new DailyRecord(timeIn, timeOut);
    } catch (IndexOutOfBoundsException e) {
      return new DailyRecord(timeIn);
    }
  }
}
