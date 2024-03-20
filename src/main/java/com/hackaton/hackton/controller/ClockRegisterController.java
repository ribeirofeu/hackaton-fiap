package com.hackaton.hackton.controller;

import com.hackaton.hackton.model.dto.RegisterRequestDTO;
import com.hackaton.hackton.model.dto.RegisterResponseDTO;
import com.hackaton.hackton.service.ClockRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clock-register")
public class ClockRegisterController {

  @Autowired private ClockRegisterService service;

  @PostMapping
  public ResponseEntity<RegisterResponseDTO> register(
      @RequestBody RegisterRequestDTO request, @RequestHeader("User-ID") UUID userId) {
    return ResponseEntity.ok(service.register(request, userId));
  }
}
