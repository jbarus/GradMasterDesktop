package com.github.jbarus.gradmasterdesktop.models.dto;

import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationDTO {
    private UUID id;
    private List<UniversityEmployee> relationList;
}
