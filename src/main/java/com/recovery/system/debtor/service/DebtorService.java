package com.recovery.system.debtor.service;

import com.recovery.system.debtor.api.dto.DebtorRequest;
import com.recovery.system.debtor.api.dto.DebtorResponse;
import com.recovery.system.debtor.domain.Debtor;
import com.recovery.system.debtor.repository.DebtorRepository;
import com.recovery.system.shared.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DebtorService {

    private final DebtorRepository debtorRepository;

    @Transactional
    public DebtorResponse createDebtor(DebtorRequest request) {
        log.info("Iniciando cadastro de novo devedor. Documento: {}", request.document());

        // 1. REGRA DE NEGÓCIO: Verificar duplicidade
        debtorRepository.findByDocument(request.document())
                .ifPresent(debtor -> {
                    // Se entrou aqui, o Optional NÃO estava vazio, então o devedor já existe.
                    log.warn("Tentativa de cadastro duplicado para o documento: {}", request.document());
                    throw new ResourceAlreadyExistsException("Já existe um devedor cadastrado com este documento.");
                });

        // 2. Mapeamento (DTO -> Entidade)
        Debtor newDebtor = new Debtor();
        newDebtor.setName(request.name());
        newDebtor.setDocument(request.document());
        newDebtor.setPersonType(request.personType());
        newDebtor.setEmail(request.email());
        newDebtor.setPhone(request.phone());

        // 3. Persistência
        Debtor savedDebtor = debtorRepository.save(newDebtor);
        log.info("Devedor cadastrado com sucesso. ID: {}", savedDebtor.getId());

        // 4. Mapeamento (Entidade -> DTO)
        return new DebtorResponse(
                savedDebtor.getId(),
                savedDebtor.getName(),
                savedDebtor.getDocument(),
                savedDebtor.getPersonType(),
                savedDebtor.getEmail(),
                savedDebtor.getPhone(),
                savedDebtor.getCreatedAt()
        );
    }
}
