package com.github.jbarus.gradmasterdesktop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UniversityEmployeeRelation {
    private UniversityEmployee universityEmployee1;
    private UniversityEmployee universityEmployee2;


    public static List<UniversityEmployeeRelation> convertListToRelation(List<UniversityEmployee> employees){
        List<UniversityEmployeeRelation> relations = new ArrayList<>();
        for (int i = 0; i < employees.size(); i += 2) {
            UniversityEmployee employee1 = employees.get(i);
            UniversityEmployee employee2 = (i + 1 < employees.size()) ? employees.get(i + 1) : null;
            relations.add(new UniversityEmployeeRelation(employee1, employee2));
        }
        return relations;
    }

    public static List<UniversityEmployee> convertRelationToList(List<UniversityEmployeeRelation> relations){
        List<UniversityEmployee> employees = new ArrayList<>();
        for (UniversityEmployeeRelation relation : relations) {
            employees.add(relation.getUniversityEmployee1());
            employees.add(relation.getUniversityEmployee2());
        }
        return employees;
    }

}
