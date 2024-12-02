package com.github.jbarus.gradmasterdesktop.models.dto;

import com.github.jbarus.gradmasterdesktop.models.Student;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private UUID id;
    private List<Student> students;
}
