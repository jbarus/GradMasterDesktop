package com.github.jbarus.gradmasterdesktop.models.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContextDisplayInfoDTO {
    private UUID id;
    private String name;
    private LocalDate date;
}
