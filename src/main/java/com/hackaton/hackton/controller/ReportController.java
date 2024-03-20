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
@RequestMapping("/clock-report")
public class ReportController {

  @Autowired private ReportService service;

  @GetMapping
  ResponseEntity<Report> getReport(
      @RequestHeader("User-ID") UUID userId,
      @RequestParam LocalDate startDate,
      LocalDate finalDate) {

    return ResponseEntity.ok(service.genereteReport(startDate, finalDate, userId));
  }
}
