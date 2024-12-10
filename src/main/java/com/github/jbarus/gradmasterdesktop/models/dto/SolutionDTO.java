package com.github.jbarus.gradmasterdesktop.models.dto;


import com.github.jbarus.gradmasterdesktop.models.Committee;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionDTO {
    private UUID id;
    private List<Committee> committees = new ArrayList<>();
    private List<Student> unassignedStudents = new ArrayList<>();
    private List<UniversityEmployee> unassignedUniversityEmployees = new ArrayList<>();
}
