package com.ninjaapps.android.crosswords.puzzlemodel;

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
        this.width      = template.getWidth();
        this.height     = template.getHeight();
        this.template   = template;

        horizontalDefinitions   = new CrosswordDefinitionMap();
        verticalDefinitions     = new CrosswordDefinitionMap();
    }

    /**
     * assigns a word definition to the board
     * @param definition
     * @param x the row
     * @param y the column
     * @param horizontal
     * @return
     */
    public boolean assign(CrosswordDefinition definition, byte x, byte y, boolean horizontal) {
        if (!definition.isValid()) {
            return false;
        }
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        if (!template.setWord(definition.getSolution(), x, y, horizontal)) {
            return false;
        }
        if (horizontal) {
            horizontalDefinitions.add(x, y, definition);
        }
        else {
            verticalDefinitions.add(x, y, definition);
        }
        return true;
    }

    /**
     * retrieves a definition from the board
     * @param x
     * @param y
     * @param horizontal
     * @return
     */
    public CrosswordDefinition getDefinition(byte x, byte y, boolean horizontal) {
        return horizontal ? horizontalDefinitions.get(x, y) : verticalDefinitions.get(x, y);
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
