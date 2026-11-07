package com.bankcore.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Transactional
@Table(name = "Transaction")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Size(min = 16, max = 16, message = "Transaction ID must be 16 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Transaction ID must be uppercase alphanumeric")
    @Column(name = "transactionId", updatable = false, nullable = false)
    private String transactionId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "completedTime", nullable = false)
    private LocalDateTime completedTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionMode;
    public enum TransactionType {
        DEPOSIT, WITHDRAW, REVERSAL
    }

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    public enum TransactionStatus{
        INITIATED, PROCESSING, COMPLETED
    }

    @Enumerated(EnumType.STRING)
    private ActionType actionType;
    public enum ActionType {
        DEBIT, CREDIT
    }

    private String senderAccountNumber;
    private String receiverAccountNumber;
    private String remarks;

}
