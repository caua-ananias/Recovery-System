package com.recovery.system.debtor.repository;

import com.recovery.system.debtor.domain.Debtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebtorRepository extends JpaRepository<Debtor, Long> {

    Optional<Debtor> findByDocument(String document);
}
