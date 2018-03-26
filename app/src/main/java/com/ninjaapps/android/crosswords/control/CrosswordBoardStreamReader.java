package com.ninjaapps.android.crosswords.control;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinitionMap;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordTemplate;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordBoardStreamReader {
    private CrosswordBoard board;

    private CrosswordBoardStreamHeader readHeader(InputStream stream) {
        byte[] buffer = new byte[CrosswordBoardStreamHeader.BUFFER_SIZE];
        try {
            stream.read(buffer, 0, CrosswordBoardStreamHeader.BUFFER_SIZE);
        } catch (IOException e) {
            return null;
        }
        CrosswordBoardStreamHeader header = new CrosswordBoardStreamHeader(buffer);
        return header;
    }

    private CrosswordTemplate readTemplate(InputStream stream, byte boardWidth, byte boardHeight) {
        CrosswordTemplate template = new CrosswordTemplate(boardWidth, boardHeight);
        for (byte i = 0; i < boardWidth; ++i) {
            for (byte j = 0; j < boardHeight; ++j) {
                try {
                    template.setAt(i, j, (char)stream.read());
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return template;
    }

    private CrosswordDefinitionMap readDefinitions(InputStream stream) {
        //TODO
        return null;
    }

    public boolean read(InputStream stream){
        CrosswordBoardStreamHeader header = readHeader(stream);
        if (!header.parse() || !header.isValid()) {
            return false;
        }

        CrosswordTemplate template = readTemplate(stream, header.getBoardWidth(), header.getBoardHeight());
        CrosswordDefinitionMap horizontalDefinitions = readDefinitions(stream);
        CrosswordDefinitionMap verticalDefinitions = readDefinitions(stream);

        if (template == null || horizontalDefinitions == null || verticalDefinitions == null)  {
            return false;
        }

        board = new CrosswordBoard(template);
        board.setHorizontalDefinitions(horizontalDefinitions);
        board.setVerticalDefinitions(verticalDefinitions);

        return true;
    }

    public CrosswordBoard getBoard() {
        return board;
    }
}
