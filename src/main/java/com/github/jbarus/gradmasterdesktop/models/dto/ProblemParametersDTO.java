package com.github.jbarus.gradmasterdesktop.models.dto;

import com.github.jbarus.gradmasterdesktop.models.ProblemParameters;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemParametersDTO {
    private UUID id;
    private ProblemParameters problemParameters;
}
