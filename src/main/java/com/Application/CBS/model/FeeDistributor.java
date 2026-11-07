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
@Table(name = "FeeDistributor")
@EntityListeners(AuditingEntityListener.class)
public class FeeDistributor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feeDistributorId;

    @Column(nullable = false,unique = true)
    private String distributorName;

    @Column(nullable = false,unique = true)
    private String distributorAccountNumber;

    @Column(nullable = false)
    private boolean isActive;

    @CreationTimestamp
    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;

    private String description;

    @OneToMany(mappedBy = "feeDistributor", fetch =  FetchType.EAGER)
    @JsonIgnore
    private List<TranFeeDistribution> tranFeeDistribution  = new ArrayList<>();

}
