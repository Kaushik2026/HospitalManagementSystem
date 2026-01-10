package com.backendlld.hospitalManagement.model;

import com.backendlld.hospitalManagement.model.enums.BloodGroupType;
import com.backendlld.hospitalManagement.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Patient extends Base{

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "patient",orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Appointment> appointments=new ArrayList<>();

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    private Insurance insurance;
//    persist is used to save the insurance when patient is saved for the first time
//    and merge is used to update the insurance when patient is updated.

}
