package com.bankcore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Transactional
@Table(name = "TranType")
@EntityListeners(AuditingEntityListener.class)
public class TranType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tranTypeId;

    private String receiverAccountNumber;
    private String senderAccountNumber;
    private String transactionGroup;
    private String receiverAccountType;
    private String senderAccountType;
    private boolean isActive;
    private boolean isFeeApplicable;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TypeCode typeCode;

    public enum TypeCode {
        CASH_DEP, CHEQUE_DEP, DIRECT_DEP, ONLINE_DEP,
        CASH_WITH, ATM_WITH, CHEQUE_WITH, ONLINE_WITH,
        INTERNAL_TRF, DOMESTIC_TRF, INTL_TRF, STANDING_ORD,
        RTGS_TRF, LOAN_DISB, LOAN_REPAY, EMI_DEDUCT, UTIL_PAY,
        CARD_PAY, SUBS_PAY, FIXED_DEP, FIXED_WITH, MUTUAL_BUY,
        MUTUAL_RED, ACC_OPEN, ACC_CLOSE, BAL_ENQUIRY, OVERDRAFT_USE,
        CARD_TOPUP, CARD_FEE, INT_CREDIT, INT_DEBIT, SERV_CHARGE, PENALTY_FEE,
        CURR_EXCH, INT_REMIT, GOVT_PAY, REVERSAL, REFUND
    }

    @CreationTimestamp
    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;
    private String description;

    @OneToMany(mappedBy = "tranType", fetch =  FetchType.EAGER)
    @JsonIgnore
    private List<TranFee> tranFee = new ArrayList<>();
}
