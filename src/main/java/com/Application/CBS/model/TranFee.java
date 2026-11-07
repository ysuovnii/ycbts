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
@Table(name = "TranFee")
@EntityListeners(AuditingEntityListener.class)
public class TranFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tranFeeId;

    private boolean isSlabApplicable;

    private boolean isFeeAmountFixed;
    private double feeFixedAmount;

    private boolean isFeeAmountPercentage;
    private double feePercentageAmount;

    private boolean isMaximumApplicable;
    private double feeMaximumAmount;

    @CreationTimestamp
    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private TranType tranType;

    @OneToMany(mappedBy = "tranFee", fetch =  FetchType.EAGER)
    @JsonIgnore
    private List<TranFeeDistribution> tranFeeDistribution = new ArrayList<>();

}
