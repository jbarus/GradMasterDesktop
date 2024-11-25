package com.github.jbarus.gradmasterdesktop.context;

import com.github.jbarus.gradmasterdesktop.models.Committee;
import com.github.jbarus.gradmasterdesktop.models.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterdesktop.models.Student;
import com.github.jbarus.gradmasterdesktop.models.UniversityEmployee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

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
    private ObservableList<ContextDisplayInfoDTO> contextDisplayInfoDTOS = FXCollections.observableArrayList();
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

}
