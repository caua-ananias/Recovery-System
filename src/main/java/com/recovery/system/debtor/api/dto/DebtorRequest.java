package com.recovery.system.debtor.api.dto;

import com.recovery.system.debtor.domain.PersonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public record DebtorRequest(@NotBlank(message = "O nome é obrigatório")
                            String name,
                            @NotBlank(message = "O documento é obrigatório")
                            @Size(min = 11, max = 14, message = "O documento (CPF/CNPJ) deve ter entre 11 e 14 caracteres")
                            String document,
                            @NotNull(message = "O tipo de pessoa é obrigatório")
                            PersonType personType,
                            @Email(message = "Formato de e-mail inválido")
                            String email,
                            String phone)
{}
