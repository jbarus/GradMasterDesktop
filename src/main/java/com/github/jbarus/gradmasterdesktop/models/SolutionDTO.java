package com.github.jbarus.gradmasterdesktop.models;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionDTO {
    private UUID id;
    private List<Committee> committees;
    private List<Student> unassignedStudents;
}
