package com.ninjaapps.android.crosswords.control;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;

import java.io.InputStream;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordBoardFileReader {
    private CrosswordBoard board;

    public CrosswordBoardFileReader(CrosswordBoard board) {
        this.board = board;
    }

    public boolean read(InputStream stream){
        //TODO
        return false;
    }
}
