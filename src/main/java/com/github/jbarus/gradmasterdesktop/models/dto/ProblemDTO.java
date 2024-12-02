package com.github.jbarus.gradmasterdesktop.models.dto;


import com.github.jbarus.gradmasterdesktop.models.ProblemParameters;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDTO {
    private UUID id;
    private List<UniversityEmployee> universityEmployees;
    private List<Student> students;
    private HashMap<UUID, List<UUID>> studentReviewerMapping;
    private List<UUID> positiveCorrelationMapping;
    private List<UUID> negativeCorrelationMapping;
    private List<UUID> splittedUniversityEmployees;
    private ProblemParameters problemParameters;


}
