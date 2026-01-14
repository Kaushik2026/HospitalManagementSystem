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
public class Department extends Base{
    @Column(nullable = false,unique = true)
    private String name;

    @ManyToMany
    private List<Doctor> doctors=new ArrayList<>();

    @ManyToOne
    private Doctor headOfDepartment;
}
