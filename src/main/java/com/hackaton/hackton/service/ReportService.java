package com.hackaton.hackton.service;

import com.hackaton.hackton.model.Report;
import org.springframework.core.io.ByteArrayResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ReportService {

    void generateReport(UUID userId, int month, int year);

}
