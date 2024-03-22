package com.hackaton.hackton.service.impl;

import com.hackaton.hackton.model.DailyRecord;
import com.hackaton.hackton.model.DailyReport;
import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.model.dto.RegisterRequestDTO;
import com.hackaton.hackton.model.dto.RegisterResponseDTO;
import com.hackaton.hackton.model.entity.RegisterEntity;
import com.hackaton.hackton.repository.RegisterRepository;
import com.hackaton.hackton.service.ClockRegisterService;
import com.hackaton.hackton.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Log4j2
@Service
public class ClockRegisterServiceImpl implements ClockRegisterService {
    @Autowired
    private RegisterRepository repository;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request, UUID userID) {

        LocalDateTime registerDatetime = LocalDateTime.now();
        Long protocol = Utils.generateProtocol();

        log.info("Protocol {}", protocol);

        RegisterEntity entity = RegisterEntity.builder().registerTime(registerDatetime).userID(userID).protocol(protocol).build();

        repository.save(entity);

        return RegisterResponseDTO.builder().protocol(protocol).build();

    }

    @Override
    public Report getRegisters(LocalDate startDate, LocalDate finalDate, UUID userID) {

        List<RegisterEntity> result =
                repository.findByUserIDAndRegisterTimeBetweenOrderByRegisterTimeAsc(
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
