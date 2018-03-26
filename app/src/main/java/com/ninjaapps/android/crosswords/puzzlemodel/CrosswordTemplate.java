package com.ninjaapps.android.crosswords.puzzlemodel;

import java.io.InputStream;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordTemplate {
    private int width;
    private int height;
    private char matrix[][];

    private static final char SEPARATOR = '#';

    public static CrosswordTemplate fromInputStrem(InputStream stream) {
        //TODO
        return null;
    }

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

    public void setAt(int x, int y, char c) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            matrix[x][y] = c;
        }
    }

    public char getAt(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return matrix[x][y];
        }
        return 0;
    }

    public void setSeparator(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            matrix[x][y] = SEPARATOR;
        }
    }

    public boolean isSeparator(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return matrix[x][y] == SEPARATOR;
        }
        return false;
    }

    private int getAvailableFrom(int x, int y, boolean horizontal) {
        if (isSeparator(x, y)) {
            return 0;
        }
        int i;
        if (horizontal) {
            for (i = x; i < width && !isSeparator(i, y); ++i);
            return i - x;
        }
        else {
            for (i = y; i < height && !isSeparator(x, i); ++i);
            return i - y;
        }
    }

    private int getCoordinate(int colRow, int index, boolean horizontal) {
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
     * @param colRow
     * @param index
     * @param horizontal
     * @return
     */
    public boolean setWord(String word, int colRow, int index, boolean horizontal) {
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
