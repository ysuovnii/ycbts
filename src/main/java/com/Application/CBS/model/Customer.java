package com.bankcore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Transactional
@Table(name = "Customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "customerId_seq_gen")
    @SequenceGenerator(name = "customerId_seq_gen", sequenceName = "customerId_sequence", initialValue = 100, allocationSize = 1)
    private BigInteger id;

    @CreationTimestamp
    @Column(name = "createdTime", nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "lastUpdatedTime")
    private LocalDateTime lastUpdatedTime;

    @Column(name = "deactivatedTime")
    private LocalDateTime deactivatedTime;

    @Column(name = "deletedTime")
    private LocalDateTime deletedTime;

    @Column(nullable = false)
    private String customerFirstName;

    private String customerMiddleName;

    @Column(nullable = false)
    private String customerLastName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String customerMobile;

    private Date customerDob;
    private String customerPresentAddress;
    private String customerPermanentAddress;
    private String customerSsn;
    private String customerTin;
    private String customerDrivingLicenseNumber;

    @OneToMany(mappedBy = "customer", fetch =  FetchType.EAGER)
    @JsonIgnore
    private List<Account> account = new ArrayList<>();
}
