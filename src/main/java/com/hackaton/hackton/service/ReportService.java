package com.hackaton.hackton.service;

import com.hackaton.hackton.model.Report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ReportService {


    Report genereteReport(LocalDate startDate, LocalDate finalDate, UUID userId);


}
