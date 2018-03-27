package com.ninjaapps.android.crosswords.puzzlemodel;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordTemplate {
    private byte width;
    private byte height;
    private char matrix[][];

    private static final char SEPARATOR = '#';

    public CrosswordTemplate(byte width, byte height) {
        this.width = width;
        this.height = height;
        this.matrix = new char[width][height];
    }

    public byte getWidth() {
        return width;
    }

    public byte getHeight() {
        return height;
    }

    private boolean isWithinBounds(byte x, byte y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void setAt(byte x, byte y, char c) {
        if (isWithinBounds(x, y)) {
            matrix[x][y] = c;
        }
    }

    public char getAt(byte x, byte y) {
        if (isWithinBounds(x, y)) {
            return matrix[x][y];
        }
        return 0;
    }

    public void setSeparator(byte x, byte y) {
        if (isWithinBounds(x, y)) {
            matrix[x][y] = SEPARATOR;
        }
    }

    public boolean isSeparator(byte x, byte y) {
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
    public byte getAvailableFrom(byte x, byte y, boolean horizontal) {
        if (isSeparator(x, y) || !isWithinBounds(x, y)) {
            return 0;
        }
        byte i;
        if (horizontal) {
            for (i = x; i < width && !isSeparator(i, y); ++i);
            return (byte)(i - x);
        }
        else {
            for (i = y; i < height && !isSeparator(x, i); ++i);
            return (byte)(i - y);
        }
    }

    /**
     * calculates the start point of the word at (x, y)
     * @param x
     * @param y
     * @param horizontal
     * @return
     */
    public byte getStartPoint(byte x, byte y, boolean horizontal) {
        if (isSeparator(x, y) || !isWithinBounds(x, y)) {
            return -1;
        }
        if (horizontal) {
            byte startX;
            for (startX = x; startX > 0 && !isSeparator((byte)(startX - 1), y); --startX);
            return startX;
        }
        else {
            byte startY;
            for (startY = y; startY > 0 && !isSeparator(x, (byte)(startY - 1)); --startY);
            return startY;
        }
    }

    /**
     * calculates the number of contiguous spaces that can fit words
     * @param colRow
     * @param horizontal
     * @return
     */
    public byte getAvailableCount(byte colRow, boolean horizontal) {
        byte start = 0;
        byte count = 0;
        while (start < (horizontal ? width : height)) {
            byte x = horizontal ? start : colRow;
            byte y = horizontal ? colRow : start;
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
    public boolean setWord(String word, byte x, byte y, boolean horizontal) {
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
