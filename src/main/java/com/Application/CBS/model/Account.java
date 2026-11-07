package com.bankcore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Data
@NoArgsConstructor
@Entity
@Transactional
@Table(name = "Account")
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "accountId_seq_gen")
    @SequenceGenerator(name = "accountId_seq_gen", sequenceName = "accountId_sequence", initialValue = 100000, allocationSize = 1)
    private Long id;

    @Column(unique = true, updatable = false, nullable = false)
    private String accountNumber;
    @PrePersist
    public void generateCustomAccountId() {
        String part1 = String.format("%04d", new Random().nextInt(10000)); // Random 4-digit number
        String part2 = String.format("%03d", new Random().nextInt(1000));  // Random 3-digit number
        String part3 = id.toString();

        this.accountNumber = part2 + "-" + part1 + "-" + part3 ;
    }

    @CreationTimestamp
    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public enum AccountType {
        SAVINGS, CURRENT, DPS, FDR
    }

    @Column(nullable = false)
    private String AccountStatus;

    private double installmentAmount;
    private double installmentTenure;
    private double installmentCompleted;
    private String accountName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Customer customer;
}