package com.hackaton.hackton.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyRecord {

    @JsonProperty(value = "entrada", index = 0)
    private final LocalDateTime timeIn;

    @JsonProperty(value = "saida", index = 1)
    private final LocalDateTime timeOut;

    @JsonProperty(value = "tempo", index = 2)
    private final LocalTime workTime;

    @JsonProperty(value = "mensagem", index = 3)
    private final String message;


    public DailyRecord(LocalDateTime timeIn, LocalDateTime timeOut){
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        workTime = LocalTime.ofSecondOfDay(timeIn.until(timeOut, ChronoUnit.SECONDS));
        message = "Marcação OK";
    }

    public DailyRecord(LocalDateTime timeIn){
        this.timeIn = timeIn;
        this.timeOut = null;
        workTime = LocalTime.MIN;
        message = "Ponto Inválido";
    }
}
