package com.github.jbarus.gradmasterdesktop.models.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemParametersDTO {
    private UUID id;
    private int committeeSize;
    private int maxNumberOfNonHabilitatedEmployees;
    private int calculationTimeInSeconds;
}
