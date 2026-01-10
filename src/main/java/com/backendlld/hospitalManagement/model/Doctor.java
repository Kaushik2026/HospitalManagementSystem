package com.backendlld.hospitalManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Doctor extends Base {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String specialization;
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "doctor")
    @ToString.Exclude
    private List<Appointment> appointments=new ArrayList<>();

    @ManyToMany(mappedBy = "doctors")
    @ToString.Exclude
    private List<Department> departments=new ArrayList<>();
}
