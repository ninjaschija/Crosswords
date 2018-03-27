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
        template.setSeparator((byte)0, (byte)0);
        template.setSeparator((byte)1, (byte)1);
        template.setSeparator((byte)2, (byte)2);
        template.setSeparator((byte)3, (byte)3);
        template.setSeparator((byte)4, (byte)4);
        template.setAt((byte)5, (byte)5, 'a');
        template.setAt((byte)6, (byte)6, 'b');
        template.setAt((byte)7, (byte)7, 'c');
        template.setAt((byte)8, (byte)8, 'd');
        template.setAt((byte)9, (byte)9, 'e');

        assertTrue(template.isSeparator((byte)0, (byte)0));
        assertTrue(template.isSeparator((byte)1, (byte)1));
        assertTrue(template.isSeparator((byte)2, (byte)2));
        assertTrue(template.isSeparator((byte)3, (byte)3));
        assertTrue(template.isSeparator((byte)4, (byte)4));

        assertEquals('a', template.getAt((byte)5, (byte)5));
        assertEquals('b', template.getAt((byte)6, (byte)6));
        assertEquals('c', template.getAt((byte)7, (byte)7));
        assertEquals('d', template.getAt((byte)8, (byte)8));
        assertEquals('e', template.getAt((byte)9, (byte)9));

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
            template.setSeparator((byte)(width + 1), i);
        }
        for (byte i = 0; i < width; i++) {
            template.setAt(i, (byte)(height + 1), 'a');
        }

        for (byte i = 0; i < height; i++) {
            assertFalse(template.isSeparator((byte)(width + 1), i));
        }
        for (byte i = 0; i < width; i++) {
            assertEquals(0, template.getAt(i, (byte)(height + 1)));
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
        template.setSeparator((byte)4, (byte)0);
        template.setSeparator((byte)1, (byte)1);
        template.setSeparator((byte)2, (byte)2);
        template.setSeparator((byte)1, (byte)3);

        assertEquals(4, template.getAvailableFrom((byte)0, (byte)0, true));
        assertEquals(1, template.getAvailableFrom((byte)0, (byte)1, true));
        assertEquals(2, template.getAvailableFrom((byte)0, (byte)2, true));
        assertEquals(1, template.getAvailableFrom((byte)0, (byte)3, true));
        assertEquals(5, template.getAvailableFrom((byte)0, (byte)4, true));
        assertEquals(3, template.getAvailableFrom((byte)2, (byte)1, true));
        assertEquals(2, template.getAvailableFrom((byte)3, (byte)2, true));
        assertEquals(3, template.getAvailableFrom((byte)2, (byte)3, true));

        assertEquals(5, template.getAvailableFrom((byte)0, (byte)0, false));
        assertEquals(1, template.getAvailableFrom((byte)1, (byte)0, false));
        assertEquals(2, template.getAvailableFrom((byte)2, (byte)0, false));
        assertEquals(5, template.getAvailableFrom((byte)3, (byte)0, false));
        assertEquals(1, template.getAvailableFrom((byte)1, (byte)2, false));
        assertEquals(1, template.getAvailableFrom((byte)1, (byte)4, false));
        assertEquals(2, template.getAvailableFrom((byte)2, (byte)3, false));
        assertEquals(4, template.getAvailableFrom((byte)4, (byte)1, false));

        assertEquals(0, template.getAvailableFrom((byte)4, (byte)0, true));
        assertEquals(0, template.getAvailableFrom((byte)1, (byte)3, true));
        assertEquals(0, template.getAvailableFrom((byte)1, (byte)1, true));
        assertEquals(0, template.getAvailableFrom((byte)2, (byte)2, true));
        assertEquals(0, template.getAvailableFrom((byte)4, (byte)0, false));
        assertEquals(0, template.getAvailableFrom((byte)1, (byte)3, false));
        assertEquals(0, template.getAvailableFrom((byte)1, (byte)1, false));
        assertEquals(0, template.getAvailableFrom((byte)2, (byte)2, false));
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
        template.setSeparator((byte)4, (byte)0);
        template.setSeparator((byte)1, (byte)1);
        template.setSeparator((byte)2, (byte)2);
        template.setSeparator((byte)1, (byte)3);

        assertEquals(0, template.getAvailableFrom((byte)(width + 1), (byte)(height + 1), true));
        assertEquals(0, template.getAvailableFrom((byte)(width + 1), (byte)(height + 1), false));
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
        template.setSeparator((byte)4, (byte)0);
        template.setSeparator((byte)1, (byte)1);
        template.setSeparator((byte)2, (byte)2);
        template.setSeparator((byte)1, (byte)3);

        assertEquals(0, template.getStartPoint((byte)3, (byte)0, true));
        assertEquals(2, template.getStartPoint((byte)4, (byte)1, true));
        assertEquals(3, template.getStartPoint((byte)4, (byte)2, true));
        assertEquals(2, template.getStartPoint((byte)4, (byte)3, true));
        assertEquals(0, template.getStartPoint((byte)4, (byte)4, true));

        assertEquals(0, template.getStartPoint((byte)0, (byte)4, false));
        assertEquals(4, template.getStartPoint((byte)1, (byte)4, false));
        assertEquals(2, template.getStartPoint((byte)1, (byte)2, false));
        assertEquals(3, template.getStartPoint((byte)2, (byte)4, false));
        assertEquals(0, template.getStartPoint((byte)3, (byte)4, false));
        assertEquals(1, template.getStartPoint((byte)4, (byte)4, false));
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
        template.setSeparator((byte) 4, (byte) 0);
        template.setSeparator((byte) 1, (byte) 1);
        template.setSeparator((byte) 2, (byte) 2);
        template.setSeparator((byte) 1, (byte) 3);

        assertEquals(-1, template.getStartPoint((byte)1, (byte)1, true));
        assertEquals(-1, template.getStartPoint((byte)(width + 1), (byte)(height + 1), true));
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
        template.setSeparator((byte) 4, (byte) 0);
        template.setSeparator((byte) 1, (byte) 1);
        template.setSeparator((byte) 2, (byte) 2);
        template.setSeparator((byte) 1, (byte) 3);

        assertEquals(1, template.getAvailableCount((byte)0, true));
        assertEquals(1, template.getAvailableCount((byte)1, true));
        assertEquals(2, template.getAvailableCount((byte)2, true));
        assertEquals(1, template.getAvailableCount((byte)3, true));
        assertEquals(1, template.getAvailableCount((byte)4, true));

        assertEquals(1, template.getAvailableCount((byte)0, false));
        assertEquals(0, template.getAvailableCount((byte)1, false));
        assertEquals(2, template.getAvailableCount((byte)2, false));
        assertEquals(1, template.getAvailableCount((byte)3, false));
        assertEquals(1, template.getAvailableCount((byte)4, false));
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
        template.setSeparator((byte) 4, (byte) 0);
        template.setSeparator((byte) 1, (byte) 1);
        template.setSeparator((byte) 2, (byte) 2);
        template.setSeparator((byte) 1, (byte) 3);

        assertEquals(0, template.getAvailableCount((byte)(width + 1), true));
        assertEquals(0, template.getAvailableCount((byte)(height + 1), false));
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
        template.setSeparator((byte) 4, (byte) 0);
        template.setSeparator((byte) 1, (byte) 1);
        template.setSeparator((byte) 2, (byte) 2);
        template.setSeparator((byte) 1, (byte) 3);

        assertTrue(template.setWord("fart", (byte)0, (byte)0, true));
        assertTrue(template.setWord("arc", (byte)2, (byte)1, true));
        assertTrue(template.setWord("re", (byte)0, (byte)2, true));
        assertTrue(template.setWord("ua", (byte)3, (byte)2, true));
        assertTrue(template.setWord("sen", (byte)2, (byte)3, true));
        assertTrue(template.setWord("snore", (byte)0, (byte)4, true));

        assertTrue(template.setWord("forks", (byte)0, (byte)0, false));
        assertTrue(template.setWord("ra", (byte)2, (byte)0, false));
        assertTrue(template.setWord("so", (byte)2, (byte)3, false));
        assertTrue(template.setWord("truer", (byte)3, (byte)0, false));
        assertTrue(template.setWord("cane", (byte)4, (byte)1, false));
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
        template.setSeparator((byte) 4, (byte) 0);
        template.setSeparator((byte) 1, (byte) 1);
        template.setSeparator((byte) 2, (byte) 2);
        template.setSeparator((byte) 1, (byte) 3);

        assertTrue(template.setWord("fart", (byte) 0, (byte) 0, true));
        assertTrue(template.setWord("arc", (byte) 2, (byte) 1, true));
        assertTrue(template.setWord("re", (byte) 0, (byte) 2, true));
        assertTrue(template.setWord("ua", (byte) 3, (byte) 2, true));
        assertTrue(template.setWord("sen", (byte) 2, (byte) 3, true));
        assertTrue(template.setWord("snore", (byte) 0, (byte) 4, true));

        assertFalse(template.setWord("nonsense", (byte)0, (byte)0, false));
        assertFalse(template.setWord("forks", (byte)0, (byte)1, false));
        assertFalse(template.setWord("test", (byte)1, (byte)0, false));
    }
}
