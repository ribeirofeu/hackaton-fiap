package com.hackaton.hackton.service;

import com.hackaton.hackton.model.Report;
import com.hackaton.hackton.model.dto.RegisterResponseDTO;
import com.hackaton.hackton.model.dto.RegisterRequestDTO;

import java.time.LocalDate;
import java.util.UUID;

public interface ClockRegisterService {


    Report getRegisters(LocalDate startDate, LocalDate finalDate, UUID userId);

    RegisterResponseDTO register(RegisterRequestDTO request, UUID userId);

}
