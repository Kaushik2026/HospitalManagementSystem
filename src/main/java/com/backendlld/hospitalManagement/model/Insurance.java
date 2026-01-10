package com.backendlld.hospitalManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Insurance extends Base{

    @Column(nullable = false)
    private String providerName;
    @Column(nullable = false)
    private String policyNumber;
    @Column(nullable = false)
    private LocalDate validTill;

    @OneToOne(mappedBy = "insurance")
    private Patient patient;
}
