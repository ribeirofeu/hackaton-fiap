package com.hackaton.hackton.controller;

import com.hackaton.hackton.service.ReportService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {

  @Autowired private ReportService service;

  @GetMapping("/clock-report")
  ResponseEntity<String> getClockReport(@RequestHeader("User-ID") UUID userId, int month, int year) {
    service.generateReport(userId, month, year);
    return ResponseEntity.ok("Report gerado com sucesso");
  }
}

