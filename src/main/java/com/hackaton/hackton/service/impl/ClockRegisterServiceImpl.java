package com.hackaton.hackton.service.impl;

import com.hackaton.hackton.model.dto.RegisterRequestDTO;
import com.hackaton.hackton.model.dto.RegisterResponseDTO;
import com.hackaton.hackton.model.entity.RegisterEntity;
import com.hackaton.hackton.repository.RegisterRepository;
import com.hackaton.hackton.service.ClockRegisterService;
import com.hackaton.hackton.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Service
public class ClockRegisterServiceImpl implements ClockRegisterService {


    @Autowired
    private RegisterRepository repository;


    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request, UUID userID) {

        LocalDateTime registerDatetime = LocalDateTime.now();
        Long protocol = Utils.generateProtocol();

        log.info("Protocol {}", protocol);

        RegisterEntity entity = RegisterEntity.builder().registerTime(registerDatetime).userID(userID).protocol(protocol).build();

        repository.save(entity);

        return RegisterResponseDTO.builder().protocol(protocol).build();

    }
}
