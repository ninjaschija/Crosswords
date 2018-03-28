package com.ninjaapps.android.crosswords.puzzlemodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CrosswordBoardUnitTest {

    @Test
    public void assignWord_positive() throws Exception {
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

        assertEquals(6, board.getHorizontalDefinitions().getAllKeys().size());
        assertEquals(5, board.getVerticalDefinitions().getAllKeys().size());

        assertEquals("fart", board.getDefinition(0, 0, true).getSolution());
        assertEquals("test1", board.getDefinition(0, 0, true).getDefinition());

        assertEquals("forks", board.getDefinition(0, 0, false).getSolution());
        assertEquals("test7", board.getDefinition(0, 0, false).getDefinition());
    }

    @Test
    public void revealSolution_positive() throws Exception {
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

        assertEquals(0, board.getBoardTile(0, 0));
        assertEquals(0, board.getBoardTile(1, 0));
        assertEquals(0, board.getBoardTile(2, 0));
        assertEquals(0, board.getBoardTile(3, 0));
        assertEquals(1, board.getBoardTile(4, 0));

        board.revealSolution(0, 0, true);

        assertEquals('f', board.getBoardTile(0, 0));
        assertEquals('a', board.getBoardTile(1, 0));
        assertEquals('r', board.getBoardTile(2, 0));
        assertEquals('t', board.getBoardTile(3, 0));
        assertEquals(1, board.getBoardTile(4, 0));

        assertEquals(0, board.getBoardTile(0, 1));
        assertEquals(1, board.getBoardTile(1, 1));
        assertEquals(0, board.getBoardTile(2, 1));
        assertEquals(0, board.getBoardTile(3, 1));
        assertEquals(0, board.getBoardTile(4, 1));

        assertEquals('t', board.getBoardTile(3, 0));
        assertEquals(0, board.getBoardTile(3, 1));
        assertEquals(0, board.getBoardTile(3, 2));
        assertEquals(0, board.getBoardTile(3, 3));
        assertEquals(0, board.getBoardTile(3, 4));

        board.revealSolution(3, 3, false);

        assertEquals('t', board.getBoardTile(3, 0));
        assertEquals('r', board.getBoardTile(3, 1));
        assertEquals('u', board.getBoardTile(3, 2));
        assertEquals('e', board.getBoardTile(3, 3));
        assertEquals('r', board.getBoardTile(3, 4));

        assertEquals('r', board.getBoardTile(2, 0));
        assertEquals(0, board.getBoardTile(2, 1));
        assertEquals(1, board.getBoardTile(2, 2));
        assertEquals(0, board.getBoardTile(2, 3));
        assertEquals(0, board.getBoardTile(2, 4));

        assertEquals(1, board.getBoardTile(4, 0));
        assertEquals(0, board.getBoardTile(4, 1));
        assertEquals(0, board.getBoardTile(4, 2));
        assertEquals(0, board.getBoardTile(4, 3));
        assertEquals(0, board.getBoardTile(4, 4));
    }
}
