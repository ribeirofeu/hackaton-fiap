package com.hackaton.hackton.model;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Email {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String address;

    private Email(String address) {
        this.address = address;
    }

    public static Email of(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!pattern.matcher(address).matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        return new Email(address.trim().toLowerCase());
    }
}
