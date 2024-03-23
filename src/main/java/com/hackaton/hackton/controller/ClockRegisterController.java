package com.hackaton.hackton.controller;

import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.model.dto.RegisterRequestDTO;
import com.hackaton.hackton.model.dto.RegisterResponseDTO;
import com.hackaton.hackton.model.entity.UserEntity;
import com.hackaton.hackton.service.ClockRegisterService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/clock-register")
public class ClockRegisterController {

  @Autowired private ClockRegisterService service;

  @PostMapping
  public ResponseEntity<RegisterResponseDTO> register(
      @RequestBody RegisterRequestDTO request) {
    UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UUID userId = principal.getId();
    return ResponseEntity.ok(service.register(request, userId));
  }

  @GetMapping
  ResponseEntity<Report> viewRegisters(
          @RequestParam LocalDate startDate,
          LocalDate finalDate) {
    UserEntity principal = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UUID userId = principal.getId();
    return ResponseEntity.ok(service.getRegisters(startDate, finalDate, userId));
  }
}
