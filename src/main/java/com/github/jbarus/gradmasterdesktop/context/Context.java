package com.github.jbarus.gradmasterdesktop.context;

import com.github.jbarus.gradmasterdesktop.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Context {
    private static Context instance;
    private UUID id;
    private ObservableList<UniversityEmployee> unassignedUniversityEmployee = FXCollections.observableArrayList();
    private ObservableList<Committee> committees = FXCollections.observableArrayList();
    private ObservableList<Student> unassignedStudents = FXCollections.observableArrayList();
    private ObservableList<UniversityEmployee> universityEmployees = FXCollections.observableArrayList();
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<UniversityEmployeeRelation> positiveRelations = FXCollections.observableArrayList();
    private ObservableList<UniversityEmployeeRelation> negativeRelations = FXCollections.observableArrayList();
    private ObservableList<ContextDisplayInfoDTO> contextDisplayInfoDTOS = FXCollections.observableArrayList();
    private LocalDate date;
    private String name;
    private Committee selectedCommittee = null;

    private Context() {
        this.id = UUID.randomUUID();
    }

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public void clearProblemData() {
        universityEmployees.clear();
        students.clear();
        positiveRelations.clear();
        negativeRelations.clear();
    }

    public void clearSolution(){
        unassignedUniversityEmployee.clear();
        committees.clear();
        unassignedStudents.clear();
        selectedCommittee = null;
    }

}
