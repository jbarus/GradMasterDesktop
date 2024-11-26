package com.github.jbarus.gradmasterdesktop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProblemParameters {
    private int committeeSize;
    private int maxNumberOfNonHabilitatedEmployees;
    private int calculationTimeInSeconds;

    public ProblemParameters(int committeeSize, int maxNumberOfNonHabilitatedEmployees, int calculationTimeInSeconds) {
        this.committeeSize = committeeSize;
        this.maxNumberOfNonHabilitatedEmployees = maxNumberOfNonHabilitatedEmployees;
        this.calculationTimeInSeconds = calculationTimeInSeconds;
    }

}
