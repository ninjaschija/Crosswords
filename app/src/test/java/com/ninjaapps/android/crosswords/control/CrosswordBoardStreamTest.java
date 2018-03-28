package com.ninjaapps.android.crosswords.control;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordBoard;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordDefinition;
import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordTemplate;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CrosswordBoardStreamTest {

    @Test
    public void writeReadTest() throws Exception {
        byte width = 5;
        byte height = 5;

        /*
        f a r t #
        o # a r c
        r e # u a
        k # s e n
        s n o r e
        */

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator( 4,  0);
        template.setSeparator( 1,  1);
        template.setSeparator( 2,  2);
        template.setSeparator( 1,  3);

        CrosswordBoard board = new CrosswordBoard(template);

        assertTrue(board.assignDefinition(new CrosswordDefinition("test1", "fart"), 0, 0, true));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test2", "arc"), 2, 1, true));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test3", "re"), 0, 2, true));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test4", "ua"), 3, 2, true));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test5", "sen"), 2, 3, true));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test6", "snore"), 0, 4, true));

        assertTrue(board.assignDefinition(new CrosswordDefinition("test7", "forks"), 0, 0, false));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test8", "ra"), 2, 0, false));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test9", "so"), 2, 3, false));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test10", "truer"), 3, 0, false));
        assertTrue(board.assignDefinition(new CrosswordDefinition("test11", "cane"), 4, 1, false));

        //to check if visibility mask is serialized correctly
        board.revealSolution(0, 0, true);  //fart
        board.revealSolution(0, 0, false); //forks

        CrosswordBoardStreamWriter writer = new CrosswordBoardStreamWriter(board);
        CrosswordBoardStreamReader reader = new CrosswordBoardStreamReader();
        ByteArrayOutputStream bois = new ByteArrayOutputStream(1024);

        assertTrue(writer.write(bois));

        byte[] savedBuffer = bois.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(savedBuffer);

        assertTrue(reader.read(bais));

        CrosswordBoard decodedBoard = reader.getBoard();

        assertEquals(board.getTemplate().getWidth(), decodedBoard.getTemplate().getWidth());
        assertEquals(board.getTemplate().getHeight(), decodedBoard.getTemplate().getHeight());

        for (int i = 0; i < board.getTemplate().getWidth(); ++i) {
            for (int j = 0; j < board.getTemplate().getHeight(); ++j) {
                assertEquals(board.getBoardTile(i, j), decodedBoard.getBoardTile(i, j));
            }
        }

        assertEquals(board.getHorizontalDefinitions().getAllKeys().size(), decodedBoard.getHorizontalDefinitions().getAllKeys().size());
        assertEquals(board.getVerticalDefinitions().getAllKeys().size(), decodedBoard.getVerticalDefinitions().getAllKeys().size());

        for (Map.Entry<Integer, Integer> entry: board.getHorizontalDefinitions().getAllKeys()) {
            CrosswordDefinition boardDef = board.getHorizontalDefinitions().get(entry);
            CrosswordDefinition decodedBoardDef = decodedBoard.getDefinition(entry.getKey(), entry.getValue(), true);

            assertNotEquals(null, decodedBoardDef);
            assertEquals(boardDef.getDefinition(), decodedBoardDef.getDefinition());
            assertEquals(boardDef.getSolution(), decodedBoardDef.getSolution());
        }

        for (Map.Entry<Integer, Integer> entry: board.getVerticalDefinitions().getAllKeys()) {
            CrosswordDefinition boardDef = board.getVerticalDefinitions().get(entry);
            CrosswordDefinition decodedBoardDef = decodedBoard.getDefinition(entry.getKey(), entry.getValue(), false);

            assertNotEquals(null, decodedBoardDef);
            assertEquals(boardDef.getDefinition(), decodedBoardDef.getDefinition());
            assertEquals(boardDef.getSolution(), decodedBoardDef.getSolution());
        }
    }
}
