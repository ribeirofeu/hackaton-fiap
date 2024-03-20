package com.hackaton.hackton.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Report {

    @JsonProperty(value = "dataInicial")
    private final LocalDate startDate;

    @JsonProperty(value = "dataFinal")
    private final LocalDate finalDate;

    @JsonProperty(value = "totalPeriodo")
    private LocalTime workPeriod;

    private UUID userID;
    private String userName;

    @Getter
    private List<DailyReport> dailyReport;


    public Report (LocalDate startDate, LocalDate finalDate, UUID userID) {
        this.startDate = startDate;
        this.finalDate = finalDate;
        this.userID = userID;
        dailyReport = new ArrayList<>();
        workPeriod = LocalTime.MIN;
    }

    public void addDaily(DailyReport report) {
        workPeriod = workPeriod.plusSeconds(report.getWorkTime().toSecondOfDay());
        this.dailyReport.add(report);
    }

}
