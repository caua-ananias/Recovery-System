package com.recovery.system.debtor.api.dto;

import com.recovery.system.debtor.domain.PersonType;

import java.time.LocalDateTime;

public record DebtorResponse(Long id,
                             String name,
                             String document,
                             PersonType personType,
                             String email,
                             String phone,
                             LocalDateTime createdAt) {
}
