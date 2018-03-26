package com.ninjaapps.android.crosswords.puzzlemodel;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordDefinition {
    private String definition;
    private String solution;

    CrosswordDefinition(String definition, String solution) {
        this.definition = definition;
        this.solution = solution;
    }

    boolean isValid() {
        return definition.length() > 0 && solution.length() > 1;
    }

    public String getDefinition() {
        return definition;
    }

    public String getSolution() {
        return solution;
    }
}
