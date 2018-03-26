package com.ninjaapps.android.crosswords.puzzlemodel;

import android.util.Pair;

import java.util.Map;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordBoard {
    private int width;
    private int height;
    private CrosswordTemplate template;
    private Map<Pair<Integer, Integer>, CrosswordDefinition> horizontalDefinitions;
    private Map<Pair<Integer, Integer>, CrosswordDefinition> verticalDefinitions;

    public CrosswordBoard(CrosswordTemplate template) {
        this.width = template.getWidth();
        this.height = template.getHeight();
        this.template = template;
    }

    /**
     * assigns a word definition to the board
     * @param definition
     * @param colRow the row or column index
     * @param index the index of the available space within the column or row
     * @param horizontal
     * @return
     */
    public boolean assign(CrosswordDefinition definition, int colRow, int index, boolean horizontal) {
        if (!definition.isValid()) {
            return false;
        }
        if (colRow < 0 || colRow >= (horizontal ? width : height)) {
            return false;
        }
        if (index < 0 || index >= template.getAvailableCount(colRow, horizontal)) {
            return false;
        }
        if (!template.setWord(definition.getSolution(), colRow, index, horizontal)) {
            return false;
        }
        if (horizontal) {
            horizontalDefinitions.put(Pair.create(colRow, index), definition);
        }
        else {
            verticalDefinitions.put(Pair.create(colRow, index), definition);
        }
        return true;
    }

    /**
     *
     * @param colRow
     * @param index
     * @param horizontal
     * @return
     */
    public CrosswordDefinition getDefinition(int colRow, int index, boolean horizontal) {
        if (horizontal) {
            return horizontalDefinitions.get(Pair.create(colRow, index));
        }
        else {
            return verticalDefinitions.get(Pair.create(colRow, index));
        }
    }

    public CrosswordTemplate getTemplate() {
        return template;
    }

    public Map<Pair<Integer, Integer>, CrosswordDefinition> getHorizontalDefinitions() {
        return horizontalDefinitions;
    }

    public void setHorizontalDefinitions(Map<Pair<Integer, Integer>, CrosswordDefinition> horizontalDefinitions) {
        this.horizontalDefinitions = horizontalDefinitions;
    }

    public Map<Pair<Integer, Integer>, CrosswordDefinition> getVerticalDefinitions() {
        return verticalDefinitions;
    }

    public void setVerticalDefinitions(Map<Pair<Integer, Integer>, CrosswordDefinition> verticalDefinitions) {
        this.verticalDefinitions = verticalDefinitions;
    }
}
