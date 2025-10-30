package com.recovery.system.debtor.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "debtors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Debtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name; // Nome ou Razão Social

    @Column(nullable = false, unique = true, length = 14)
    private String document; // CPF(11) ou CNPJ(14)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PersonType personType;

    @Column(length = 100)
    private String email;

    @Column(length = 15)
    private String phone;

    @CreationTimestamp // Define a data/hora atual QUANDO a entidade é criada
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Define a data/hora atual QUANDO a entidade é atualizada
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
