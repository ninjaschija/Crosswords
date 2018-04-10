package com.ninjaapps.android.crosswords.puzzlemodel;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordBoard {
    private int width;
    private int height;
    private CrosswordTemplate template;
    private CrosswordMask visibleMask;
    private CrosswordDefinitionMap horizontalDefinitions;
    private CrosswordDefinitionMap verticalDefinitions;

    public CrosswordBoard(CrosswordTemplate template) {
        this.width      = template.getWidth();
        this.height     = template.getHeight();
        this.template   = template;

        visibleMask             = new CrosswordMask(width, height);
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
    public boolean assignDefinition(CrosswordDefinition definition, int x, int y, boolean horizontal) {
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
    public CrosswordDefinition getDefinition(int x, int y, boolean horizontal) {
        int start = template.getStartPoint(x, y, horizontal);
        if (start == -1) {
            return null;
        }
        int startX = horizontal ? start : x;
        int startY = horizontal ? y : start;
        return horizontal ?
                horizontalDefinitions.get(startX, startY) :
                verticalDefinitions.get(startX, startY);
    }

    /**
     * makes the word containing (x, y) visible on the board
     * @param x
     * @param y
     * @param horizontal
     */
    public void revealSolution(int x, int y, boolean horizontal) {
        int start = template.getStartPoint(x, y, horizontal);
        if (start == -1) {
            return;
        }
        int startX = horizontal ? start : x;
        int startY = horizontal ? y : start;
        int available = template.getAvailableFrom(startX, startY, horizontal);

        for (int i = 0; i < available; ++i) {
            if (horizontal) {
                visibleMask.setVisible(startX + i, startY, true);
            }
            else {
                visibleMask.setVisible(startX, startY + i, true);
            }
        }
    }

    /**
     * retrieves the board tile at (x, y)
     * @param x
     * @param y
     * @return 1 for separator, 0 for unrevealed tile, or the character for revealed tile
     */
    public int getBoardTile(int x, int y) {
        if (template.isSeparator(x, y)) {
            return 1;
        }
        if (!visibleMask.isVisible(x, y)) {
            return 0;
        }
        return (int)template.getAt(x, y);
    }

    public boolean isSeparator(int x, int y) {
        return template.isSeparator(x, y);
    }

    public boolean isRevealed(int x, int y) {
        return visibleMask.isVisible(x, y);
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

    public CrosswordMask getVisibleMask() {
        return visibleMask;
    }

    public void setVisibleMask(CrosswordMask visibleMask) {
        this.visibleMask = visibleMask;
    }
}
