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

@Data
@NoArgsConstructor
@Entity
@Transactional
@Table(name = "TranFeeDistribution")
@EntityListeners(AuditingEntityListener.class)
public class TranFeeDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tranFeeDistributionId;

    @Column(nullable = false)
    private String distributorName;

    @Column(nullable = false)
    private String distributorAccountNumber;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private double feePercentage;

    @CreationTimestamp
    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;

    private String description;

    @Column(nullable = false)
    private int tranFeeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private TranFee tranFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private FeeDistributor feeDistributor;

}
