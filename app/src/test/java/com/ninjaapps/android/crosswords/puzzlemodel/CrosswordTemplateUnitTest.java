package com.ninjaapps.android.crosswords.puzzlemodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ninja on 27/03/2018.
 */

public class CrosswordTemplateUnitTest {

    @Test
    public void getSet_positive() throws Exception {
        byte width = 10;
        byte height = 10;

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator(0, 0);
        template.setSeparator(1, 1);
        template.setSeparator(2, 2);
        template.setSeparator(3, 3);
        template.setSeparator(4, 4);
        template.setAt(5, 5, 'a');
        template.setAt(6, 6, 'b');
        template.setAt(7, 7, 'c');
        template.setAt(8, 8, 'd');
        template.setAt(9, 9, 'e');

        assertTrue(template.isSeparator(0, 0));
        assertTrue(template.isSeparator(1, 1));
        assertTrue(template.isSeparator(2, 2));
        assertTrue(template.isSeparator(3, 3));
        assertTrue(template.isSeparator(4, 4));

        assertEquals('a', template.getAt(5, 5));
        assertEquals('b', template.getAt(6, 6));
        assertEquals('c', template.getAt(7, 7));
        assertEquals('d', template.getAt(8, 8));
        assertEquals('e', template.getAt(9, 9));

        for (byte i = 0; i < width; ++i) {
            for (byte j = 0; j < height; ++j) {
                if (i != j) {
                    assertEquals(0, template.getAt(i, j));
                }
            }
        }
    }

    @Test
    public void getSet_negative() throws Exception {
        byte width = 10;
        byte height = 10;

        CrosswordTemplate template = new CrosswordTemplate(width, height);

        for (byte i = 0; i < height; i++) {
            template.setSeparator((width + 1), i);
        }
        for (byte i = 0; i < width; i++) {
            template.setAt(i, (height + 1), 'a');
        }

        for (byte i = 0; i < height; i++) {
            assertFalse(template.isSeparator((width + 1), i));
        }
        for (byte i = 0; i < width; i++) {
            assertEquals(0, template.getAt(i, (height + 1)));
        }
    }

    @Test
    public void getAvailableFrom_positive() throws Exception {
        byte width = 5;
        byte height = 5;

        /*
        0 0 0 0 #
        0 # 0 0 0
        0 0 # 0 0
        0 # 0 0 0
        0 0 0 0 0
        */

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator(4, 0);
        template.setSeparator(1, 1);
        template.setSeparator(2, 2);
        template.setSeparator(1, 3);

        assertEquals(4, template.getAvailableFrom(0, 0, true));
        assertEquals(1, template.getAvailableFrom(0, 1, true));
        assertEquals(2, template.getAvailableFrom(0, 2, true));
        assertEquals(1, template.getAvailableFrom(0, 3, true));
        assertEquals(5, template.getAvailableFrom(0, 4, true));
        assertEquals(3, template.getAvailableFrom(2, 1, true));
        assertEquals(2, template.getAvailableFrom(3, 2, true));
        assertEquals(3, template.getAvailableFrom(2, 3, true));

        assertEquals(5, template.getAvailableFrom(0, 0, false));
        assertEquals(1, template.getAvailableFrom(1, 0, false));
        assertEquals(2, template.getAvailableFrom(2, 0, false));
        assertEquals(5, template.getAvailableFrom(3, 0, false));
        assertEquals(1, template.getAvailableFrom(1, 2, false));
        assertEquals(1, template.getAvailableFrom(1, 4, false));
        assertEquals(2, template.getAvailableFrom(2, 3, false));
        assertEquals(4, template.getAvailableFrom(4, 1, false));

        assertEquals(0, template.getAvailableFrom(4, 0, true));
        assertEquals(0, template.getAvailableFrom(1, 3, true));
        assertEquals(0, template.getAvailableFrom(1, 1, true));
        assertEquals(0, template.getAvailableFrom(2, 2, true));
        assertEquals(0, template.getAvailableFrom(4, 0, false));
        assertEquals(0, template.getAvailableFrom(1, 3, false));
        assertEquals(0, template.getAvailableFrom(1, 1, false));
        assertEquals(0, template.getAvailableFrom(2, 2, false));
    }

    @Test
    public void getAvailableFrom_negative() throws Exception {
        byte width = 5;
        byte height = 5;

        /*
        0 0 0 0 #
        0 # 0 0 0
        0 0 # 0 0
        0 # 0 0 0
        0 0 0 0 0
        */

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator(4, 0);
        template.setSeparator(1, 1);
        template.setSeparator(2, 2);
        template.setSeparator(1, 3);

        assertEquals(0, template.getAvailableFrom((width + 1), (height + 1), true));
        assertEquals(0, template.getAvailableFrom((width + 1), (height + 1), false));
    }

    @Test
    public void getStartPoint_positive() throws Exception {
        byte width = 5;
        byte height = 5;

        /*
        0 0 0 0 #
        0 # 0 0 0
        0 0 # 0 0
        0 # 0 0 0
        0 0 0 0 0
        */

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator(4, 0);
        template.setSeparator(1, 1);
        template.setSeparator(2, 2);
        template.setSeparator(1, 3);

        assertEquals(0, template.getStartPoint(3, 0, true));
        assertEquals(2, template.getStartPoint(4, 1, true));
        assertEquals(3, template.getStartPoint(4, 2, true));
        assertEquals(2, template.getStartPoint(4, 3, true));
        assertEquals(0, template.getStartPoint(4, 4, true));

        assertEquals(0, template.getStartPoint(0, 4, false));
        assertEquals(4, template.getStartPoint(1, 4, false));
        assertEquals(2, template.getStartPoint(1, 2, false));
        assertEquals(3, template.getStartPoint(2, 4, false));
        assertEquals(0, template.getStartPoint(3, 4, false));
        assertEquals(1, template.getStartPoint(4, 4, false));
    }

    @Test
    public void getStartPoint_negative() throws Exception {
        byte width = 5;
        byte height = 5;

        /*
        0 0 0 0 #
        0 # 0 0 0
        0 0 # 0 0
        0 # 0 0 0
        0 0 0 0 0
        */

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator( 4,  0);
        template.setSeparator( 1,  1);
        template.setSeparator( 2,  2);
        template.setSeparator( 1,  3);

        assertEquals(-1, template.getStartPoint(1, 1, true));
        assertEquals(-1, template.getStartPoint((width + 1), (height + 1), true));
    }

    @Test
    public void getAvailableCount_positive() throws Exception {
        byte width = 5;
        byte height = 5;

        /*
        0 0 0 0 #
        0 # 0 0 0
        0 0 # 0 0
        0 # 0 0 0
        0 0 0 0 0
        */

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator( 4,  0);
        template.setSeparator( 1,  1);
        template.setSeparator( 2,  2);
        template.setSeparator( 1,  3);

        assertEquals(1, template.getAvailableCount(0, true));
        assertEquals(1, template.getAvailableCount(1, true));
        assertEquals(2, template.getAvailableCount(2, true));
        assertEquals(1, template.getAvailableCount(3, true));
        assertEquals(1, template.getAvailableCount(4, true));

        assertEquals(1, template.getAvailableCount(0, false));
        assertEquals(0, template.getAvailableCount(1, false));
        assertEquals(2, template.getAvailableCount(2, false));
        assertEquals(1, template.getAvailableCount(3, false));
        assertEquals(1, template.getAvailableCount(4, false));
    }

    @Test
    public void getAvailableCount_negative() throws Exception {
        byte width = 5;
        byte height = 5;

        /*
        0 0 0 0 #
        0 # 0 0 0
        0 0 # 0 0
        0 # 0 0 0
        0 0 0 0 0
        */

        CrosswordTemplate template = new CrosswordTemplate(width, height);
        template.setSeparator( 4,  0);
        template.setSeparator( 1,  1);
        template.setSeparator( 2,  2);
        template.setSeparator( 1,  3);

        assertEquals(0, template.getAvailableCount((width + 1), true));
        assertEquals(0, template.getAvailableCount((height + 1), false));
    }

    @Test
    public void setWord_positive() throws Exception {
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

        assertTrue(template.setWord("fart", 0, 0, true));
        assertTrue(template.setWord("arc", 2, 1, true));
        assertTrue(template.setWord("re", 0, 2, true));
        assertTrue(template.setWord("ua", 3, 2, true));
        assertTrue(template.setWord("sen", 2, 3, true));
        assertTrue(template.setWord("snore", 0, 4, true));

        assertTrue(template.setWord("forks", 0, 0, false));
        assertTrue(template.setWord("ra", 2, 0, false));
        assertTrue(template.setWord("so", 2, 3, false));
        assertTrue(template.setWord("truer", 3, 0, false));
        assertTrue(template.setWord("cane", 4, 1, false));
    }

    @Test
    public void setWord_negative() throws Exception {
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

        assertTrue(template.setWord("fart",  0,  0, true));
        assertTrue(template.setWord("arc",  2,  1, true));
        assertTrue(template.setWord("re",  0,  2, true));
        assertTrue(template.setWord("ua",  3,  2, true));
        assertTrue(template.setWord("sen",  2,  3, true));
        assertTrue(template.setWord("snore",  0,  4, true));

        assertFalse(template.setWord("nonsense", 0, 0, false));
        assertFalse(template.setWord("forks", 0, 1, false));
        assertFalse(template.setWord("test", 1, 0, false));
    }
}
