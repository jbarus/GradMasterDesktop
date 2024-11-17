package com.github.jbarus.gradmasterdesktop.models;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Committee {
    List<UniversityEmployee> universityEmployees;
    List<Student> students;


    public Committee(List<UniversityEmployee> universityEmployees, List<Student> students) {
        this.universityEmployees = universityEmployees;
        this.students = students;
    }

    public Committee() {
    }
}
