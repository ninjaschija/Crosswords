package com.ninjaapps.android.crosswords.control;

import android.util.Pair;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinition;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinitionMap;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * Created by ninja on 3/25/2018.
 */

public class CrosswordBoardStreamWriter {
    private CrosswordBoard board;

    public CrosswordBoardStreamWriter(CrosswordBoard board) {
        this.board = board;
    }

    private boolean writeHeader(CrosswordBoardStreamHeader header, OutputStream stream) {
        byte[] buffer = header.compose();
        try {
            stream.write(buffer);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean writeTemplate(CrosswordTemplate template, OutputStream stream) {
        byte width = template.getWidth();
        byte height = template.getHeight();
        for (byte i = 0; i < width; ++i) {
            for (byte j = 0; j < height; ++j) {
                try {
                    stream.write(template.getAt(i, j));
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean writeDefinition(CrosswordDefinition definition, OutputStream stream) {
        //TODO
        return false;
    }

    private boolean writeDefinitions(CrosswordDefinitionMap definitions, OutputStream stream) {
        Set<Pair<Byte, Byte>> keySet = definitions.getAllKeys();
        for (Pair<Byte, Byte> key: keySet) {
            CrosswordDefinition definition = definitions.get(key.first, key.second);
            try {
                stream.write(key.first);
                stream.write(key.second);
                writeDefinition(definition, stream);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public boolean write(OutputStream stream) {
        CrosswordBoardStreamHeader header = new CrosswordBoardStreamHeader(board.getTemplate().getWidth()
                , board.getTemplate().getHeight());
        if (!writeHeader(header, stream)) {
            return false;
        }
        if (!writeTemplate(board.getTemplate(), stream)) {
            return false;
        }
        if (!writeDefinitions(board.getHorizontalDefinitions(), stream)) {
            return false;
        }
        if (!writeDefinitions(board.getVerticalDefinitions(), stream)) {
            return false;
        }
        return true;
    }
}
