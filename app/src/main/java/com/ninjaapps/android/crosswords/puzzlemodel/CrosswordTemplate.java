package com.ninjaapps.android.crosswords.puzzlemodel;

import java.io.InputStream;

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

    public void setAt(byte x, byte y, char c) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            matrix[x][y] = c;
        }
    }

    public char getAt(byte x, byte y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return matrix[x][y];
        }
        return 0;
    }

    public void setSeparator(byte x, byte y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            matrix[x][y] = SEPARATOR;
        }
    }

    public boolean isSeparator(byte x, byte y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return matrix[x][y] == SEPARATOR;
        }
        return false;
    }

    private int getAvailableFrom(byte x, byte y, boolean horizontal) {
        if (isSeparator(x, y)) {
            return 0;
        }
        byte i;
        if (horizontal) {
            for (i = x; i < width && !isSeparator(i, y); ++i);
            return i - x;
        }
        else {
            for (i = y; i < height && !isSeparator(x, i); ++i);
            return i - y;
        }
    }

    private int getCoordinate(byte colRow, byte index, boolean horizontal) {
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

            if (count == index) {
                break;
            }

            if (available > 1) {
                ++count;
            }
            start += available;
        }
        return start;
    }

    /**
     * calculates the number of contiguous spaces that can fit words
     * @param colRow
     * @param horizontal
     * @return
     */
    public int getAvailableCount(byte colRow, boolean horizontal) {
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
     * @param colRow
     * @param index
     * @param horizontal
     * @return
     */
    public boolean setWord(String word, byte colRow, byte index, boolean horizontal) {
        int coord = getCoordinate(colRow, index, horizontal);
        boolean success = true;
        for (int i = 0; i < word.length(); ++i) {
            int x = horizontal ? coord + i : colRow;
            int y = horizontal ? colRow : coord + i;
            if (matrix[x][y] == 0) {
                matrix[x][y] = word.charAt(i);
            }
            if (matrix[x][y] != word.charAt(i)) {
                success = false;
                break;
            }
        }
        return success;
    }
}
