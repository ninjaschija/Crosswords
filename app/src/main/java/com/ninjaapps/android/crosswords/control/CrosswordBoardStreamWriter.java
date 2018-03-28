package com.ninjaapps.android.crosswords.control;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinition;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinitionMap;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordMask;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
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
        int width = template.getWidth();
        int height = template.getHeight();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                try {
                    stream.write(template.getAt(i, j));
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean writeVisibleMask(CrosswordMask mask, OutputStream stream) {
        int width = mask.getWidth();
        int height = mask.getHeight();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                try {
                    stream.write((byte)(mask.isVisible(i, j) ? 1 : 0));
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean writeDefinition(CrosswordDefinition definition, OutputStream stream) {
        try {
            stream.write(definition.getDefinition().getBytes());
            stream.write((byte)0);
            stream.write(definition.getSolution().getBytes());
            stream.write((byte)0);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean writeDefinitions(CrosswordDefinitionMap definitions, OutputStream stream) {
        Set<Map.Entry<Integer, Integer>> keySet = definitions.getAllKeys();
        try {
            stream.write((byte)keySet.size());
        } catch (IOException e) {
            return false;
        }
        for (Map.Entry<Integer, Integer> entry: keySet) {
            CrosswordDefinition definition = definitions.get(entry);
            try {
                int x = entry.getKey();
                int y = entry.getValue();
                stream.write((byte)x);
                stream.write((byte)y);
                if (!writeDefinition(definition, stream)) {
                    return false;
                }
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
        if (!writeVisibleMask(board.getVisibleMask(), stream)) {
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
