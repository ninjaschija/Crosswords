package com.ninjaapps.android.crosswords.control;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;

import java.io.OutputStream;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordBoardFileWriter {
    private CrosswordBoard board;

    public CrosswordBoardFileWriter(CrosswordBoard board) {
        this.board = board;
    }

    public boolean write(OutputStream stream) {
        //TODO
        return false;
    }
}
