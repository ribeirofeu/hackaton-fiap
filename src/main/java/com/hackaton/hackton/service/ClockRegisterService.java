package com.hackaton.hackton.service;

import com.hackaton.hackton.model.dto.RegisterResponseDTO;
import com.hackaton.hackton.model.dto.RegisterRequestDTO;

import java.util.UUID;

public interface ClockRegisterService {

    RegisterResponseDTO register(RegisterRequestDTO request, UUID userId);

}
