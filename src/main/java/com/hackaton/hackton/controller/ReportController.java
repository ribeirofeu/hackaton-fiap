package com.hackaton.hackton.controller;

import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.service.ReportService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
public class ReportController {

  @Autowired private ReportService service;



  @GetMapping("/clock-report")
  ResponseEntity<String> getClockReport(@RequestHeader("User-ID") UUID userId) {

    /**
     * Get Report String -> Generate report
     * Send Email (ReportString)
     */

    service.generateReport(userId, 0L, 0L);
    return ResponseEntity.ok("Report gerado com sucesso");
  }
}

