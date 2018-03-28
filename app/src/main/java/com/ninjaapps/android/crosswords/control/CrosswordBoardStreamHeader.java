package com.ninjaapps.android.crosswords.control;

/**
 * Created by ninja on 3/26/2018.
 */

public class CrosswordBoardStreamHeader {
    public final static int SIGNATURE   = 0xCBFE;
    public final static int VERSION     = 1;
    public final static int BUFFER_SIZE = 32;

    private byte[] buffer;

    private byte version;
    private int boardWidth;
    private int boardHeight;

    public CrosswordBoardStreamHeader(byte[] buffer) {
        this.buffer = buffer;
    }

    public CrosswordBoardStreamHeader(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public boolean parse() {
        if (buffer == null || buffer.length != BUFFER_SIZE) {
            return false;
        }

        if (buffer[0] != (byte)(SIGNATURE >> 8) || buffer[1] != (byte)SIGNATURE) {
            return false;
        }

        version     = buffer[2];
        boardWidth  = buffer[3];
        boardHeight = buffer[4];

        return true;
    }

    public byte[] compose() {
        buffer = new byte[BUFFER_SIZE];
        buffer[0] = (byte)(SIGNATURE >> 8);
        buffer[1] = (byte)SIGNATURE;
        buffer[2] = VERSION;
        buffer[3] = (byte)boardWidth;
        buffer[4] = (byte)boardHeight;
        return buffer;
    }

    public boolean isValid() {
        return version == VERSION && boardWidth > 0 && boardHeight > 0;
    }

    public byte getVersion() {
        return version;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
}
