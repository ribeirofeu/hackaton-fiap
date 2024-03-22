package com.hackaton.hackton.model.dto;


import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(@NotEmpty String login, @NotEmpty String password) {}