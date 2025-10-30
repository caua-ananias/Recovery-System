package com.recovery.system.debtor.api;

import com.recovery.system.debtor.api.dto.DebtorRequest;
import com.recovery.system.debtor.api.dto.DebtorResponse;
import com.recovery.system.debtor.service.DebtorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/debtors")
@RequiredArgsConstructor
// @Tag(name = "Debtor", description = "API para Gerenciamento de Devedores")
public class DebtorController {

    private final DebtorService debtorService;

    @PostMapping
    public ResponseEntity<DebtorResponse> createDebtor(@Valid @RequestBody DebtorRequest request) {
        DebtorResponse response = debtorService.createDebtor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

