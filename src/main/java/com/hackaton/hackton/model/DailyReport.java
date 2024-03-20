package com.hackaton.hackton.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DailyReport {

    @JsonProperty(value = "data")
    private LocalDate day;

    @JsonProperty(value = "registros")
    private List<DailyRecord> records;

    @JsonProperty(value = "totalDia")
    private LocalTime workTime;

    public DailyReport(LocalDate day){
        this.day = day;
        records = new ArrayList<>();
        workTime =  LocalTime.MIN;

    }

    public void addRecord(DailyRecord record) {
        workTime = workTime.plusSeconds(record.getWorkTime().toSecondOfDay());
        records.add(record);
    }

}
