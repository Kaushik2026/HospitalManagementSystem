package com.backendlld.hospitalManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
public class Department extends Base{
    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany
    private List<Doctor> doctors=new ArrayList<>();

    @OneToOne
    private Doctor headOfDepartment;
}
