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
    private CrosswordDefinitionMap horizontalDefinitions;
    private CrosswordDefinitionMap verticalDefinitions;

    public CrosswordBoard(CrosswordTemplate template) {
        this.width = template.getWidth();
        this.height = template.getHeight();
        this.template = template;

        horizontalDefinitions = new CrosswordDefinitionMap();
        verticalDefinitions = new CrosswordDefinitionMap();
    }

    /**
     * assigns a word definition to the board
     * @param definition
     * @param colRow the row or column index
     * @param index the index of the available space within the column or row
     * @param horizontal
     * @return
     */
    public boolean assign(CrosswordDefinition definition, byte colRow, byte index, boolean horizontal) {
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
            horizontalDefinitions.add(colRow, index, definition);
        }
        else {
            verticalDefinitions.add(colRow, index, definition);
        }
        return true;
    }

    /**
     * retrieves a definition from the board
     * @param colRow
     * @param index
     * @param horizontal
     * @return
     */
    public CrosswordDefinition getDefinition(byte colRow, byte index, boolean horizontal) {
        if (horizontal) {
            return horizontalDefinitions.get(colRow, index);
        }
        else {
            return verticalDefinitions.get(colRow, index);
        }
    }

    public CrosswordTemplate getTemplate() {
        return template;
    }

    public CrosswordDefinitionMap getHorizontalDefinitions() {
        return horizontalDefinitions;
    }

    public void setHorizontalDefinitions(CrosswordDefinitionMap horizontalDefinitions) {
        this.horizontalDefinitions = horizontalDefinitions;
    }

    public CrosswordDefinitionMap getVerticalDefinitions() {
        return verticalDefinitions;
    }

    public void setVerticalDefinitions(CrosswordDefinitionMap verticalDefinitions) {
        this.verticalDefinitions = verticalDefinitions;
    }
}
