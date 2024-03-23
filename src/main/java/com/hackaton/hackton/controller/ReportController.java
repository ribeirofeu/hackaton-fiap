package com.hackaton.hackton.controller;

import com.hackaton.hackton.model.entity.UserEntity;
import com.hackaton.hackton.service.ReportService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {

  @Autowired private ReportService service;

  @GetMapping("/clock-report")
  ResponseEntity<String> getClockReport(int month, int year) {
    UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UUID userId = principal.getId();
    String email = principal.getEmail().getAddress();
    service.generateReport(userId, email, month, year);
    return ResponseEntity.ok("Report gerado com sucesso");
  }
}

