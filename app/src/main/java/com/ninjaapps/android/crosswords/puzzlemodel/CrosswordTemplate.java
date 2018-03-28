package com.ninjaapps.android.crosswords.puzzlemodel;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordTemplate {
    private int width;
    private int height;
    private char matrix[][];

    private static final char SEPARATOR = '#';

    public CrosswordTemplate(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new char[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void setAt(int x, int y, char c) {
        if (isWithinBounds(x, y)) {
            matrix[x][y] = c;
        }
    }

    public char getAt(int x, int y) {
        if (isWithinBounds(x, y)) {
            return matrix[x][y];
        }
        return 0;
    }

    public void setSeparator(int x, int y) {
        if (isWithinBounds(x, y)) {
            matrix[x][y] = SEPARATOR;
        }
    }

    public boolean isSeparator(int x, int y) {
        if (isWithinBounds(x, y)) {
            return matrix[x][y] == SEPARATOR;
        }
        return false;
    }

    /**
     *
     * @param x
     * @param y
     * @param horizontal
     * @return
     */
    public int getAvailableFrom(int x, int y, boolean horizontal) {
        if (isSeparator(x, y) || !isWithinBounds(x, y)) {
            return 0;
        }
        int i;
        if (horizontal) {
            for (i = x; i < width && !isSeparator(i, y); ++i);
            return (int)(i - x);
        }
        else {
            for (i = y; i < height && !isSeparator(x, i); ++i);
            return (int)(i - y);
        }
    }

    /**
     * calculates the start point of the word at (x, y)
     * @param x
     * @param y
     * @param horizontal
     * @return
     */
    public int getStartPoint(int x, int y, boolean horizontal) {
        if (isSeparator(x, y) || !isWithinBounds(x, y)) {
            return -1;
        }
        if (horizontal) {
            int startX;
            for (startX = x; startX > 0 && !isSeparator((int)(startX - 1), y); --startX);
            return startX;
        }
        else {
            int startY;
            for (startY = y; startY > 0 && !isSeparator(x, (int)(startY - 1)); --startY);
            return startY;
        }
    }

    /**
     * calculates the number of contiguous spaces that can fit words
     * @param colRow
     * @param horizontal
     * @return
     */
    public int getAvailableCount(int colRow, boolean horizontal) {
        int start = 0;
        int count = 0;
        while (start < (horizontal ? width : height)) {
            int x = horizontal ? start : colRow;
            int y = horizontal ? colRow : start;
            int available = getAvailableFrom(x, y, horizontal);
            //separator (or end)
            if (available == 0) {
                ++start;
            }
            else if (available > 1) {
                ++count;
            }
            start += available;
        }
        return count;
    }

    /**
     *
     * @param word
     * @param x
     * @param y
     * @param horizontal
     * @return
     */
    public boolean setWord(String word, int x, int y, boolean horizontal) {
        if (!isWithinBounds(x, y)) {
            return false;
        }
        for (int i = 0; i < word.length(); ++i) {
            int xOffset = horizontal ? x + i : x;
            int yOffset = horizontal ? y : y + i;
            if (matrix[xOffset][yOffset] == 0) {
                matrix[xOffset][yOffset] = word.charAt(i);
            }
            if (matrix[xOffset][yOffset] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
