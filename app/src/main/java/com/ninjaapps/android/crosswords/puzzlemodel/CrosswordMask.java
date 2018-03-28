package com.ninjaapps.android.crosswords.puzzlemodel;

public class CrosswordMask {
    private int width;
    private int height;
    private boolean visibleMatrix[][];

    public CrosswordMask(int width, int height) {
        this.width = width;
        this.height = height;

        visibleMatrix = new boolean[width][height];
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isVisible(int x, int y) {
        if (isWithinBounds(x, y)) {
            return visibleMatrix[x][y];
        }
        return false;
    }

    public void setVisible(int x, int y, boolean visible) {
        if (isWithinBounds(x, y)) {
            visibleMatrix[x][y] = visible;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
