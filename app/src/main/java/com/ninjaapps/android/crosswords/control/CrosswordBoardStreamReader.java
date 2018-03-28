package com.ninjaapps.android.crosswords.control;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinition;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinitionMap;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordMask;
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

    private CrosswordTemplate readTemplate(InputStream stream, int boardWidth, int boardHeight) {
        CrosswordTemplate template = new CrosswordTemplate(boardWidth, boardHeight);
        for (int i = 0; i < boardWidth; ++i) {
            for (int j = 0; j < boardHeight; ++j) {
                try {
                    template.setAt(i, j, (char)stream.read());
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return template;
    }

    private CrosswordMask readVisibleMask(InputStream stream, int boardWidth, int boardHeight) {
        CrosswordMask mask = new CrosswordMask(boardWidth, boardHeight);
        for (int i = 0; i < boardWidth; ++i) {
            for (int j = 0; j < boardHeight; ++j) {
                try {
                    mask.setVisible(i, j, stream.read() > 0);
                } catch (IOException e) {
                    return null;
                }
            }
        }
        return mask;
    }

    private CrosswordDefinition readDefinition(InputStream stream) {
        CrosswordDefinition definition;
        try {
            int b;
            StringBuffer def = new StringBuffer();
            while ((b = stream.read()) > 0) {
                def.append((char)b);
            }
            if (b == -1) {
                return null;
            }
            StringBuffer sol = new StringBuffer();
            while ((b = stream.read()) > 0) {
                sol.append((char)b);
            }
            definition = new CrosswordDefinition(def.toString(), sol.toString());

        } catch (IOException e) {
            return null;
        }
        return definition;
    }

    private CrosswordDefinitionMap readDefinitions(InputStream stream) {
        int x, y, size;
        CrosswordDefinitionMap map = new CrosswordDefinitionMap();
        try {
            size = stream.read();
        } catch (IOException e) {
            return null;
        }
        for (int i = 0; i < size; ++i) {
            try {
                x = stream.read();
                if (x == -1) {
                    break;
                }
                y = stream.read();
                if (y == -1) {
                    break;
                }
                CrosswordDefinition def = readDefinition(stream);
                if (def == null) {
                    return null;
                }
                map.add(x, y, def);
            } catch (IOException e) {
                return null;
            }
        }
        return map;
    }

    public boolean read(InputStream stream){
        CrosswordBoardStreamHeader header = readHeader(stream);
        if (header == null
                || !header.parse()
                || !header.isValid()) {
            return false;
        }

        CrosswordTemplate template = readTemplate(stream, header.getBoardWidth(), header.getBoardHeight());
        CrosswordMask mask = readVisibleMask(stream, header.getBoardWidth(), header.getBoardHeight());
        CrosswordDefinitionMap horizontalDefinitions = readDefinitions(stream);
        CrosswordDefinitionMap verticalDefinitions = readDefinitions(stream);

        if (template == null
                || mask == null
                || horizontalDefinitions == null
                || verticalDefinitions == null)  {
            return false;
        }

        board = new CrosswordBoard(template);
        board.setVisibleMask(mask);
        board.setHorizontalDefinitions(horizontalDefinitions);
        board.setVerticalDefinitions(verticalDefinitions);

        return true;
    }

    public CrosswordBoard getBoard() {
        return board;
    }
}
