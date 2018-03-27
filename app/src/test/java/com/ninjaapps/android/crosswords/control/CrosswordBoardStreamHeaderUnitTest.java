package com.ninjaapps.android.crosswords.control;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by ninja on 27/03/2018.
 */

public class CrosswordBoardStreamHeaderUnitTest {

    @Test
    public void parse_success() throws Exception {
        byte width = 6;
        byte height = 8;
        byte[] buffer = new byte[CrosswordBoardStreamHeader.BUFFER_SIZE];
        buffer[0] = (byte)0xCB;
        buffer[1] = (byte)0xFE;
        buffer[2] = CrosswordBoardStreamHeader.VERSION;
        buffer[3] = width;
        buffer[4] = height;

        CrosswordBoardStreamHeader header = new CrosswordBoardStreamHeader(buffer);

        assertTrue(header.parse());
        assertTrue(header.isValid());
        assertEquals(CrosswordBoardStreamHeader.VERSION, header.getVersion());
        assertEquals(width, header.getBoardWidth());
        assertEquals(height, header.getBoardHeight());
    }

    @Test
    public void parse_success_wrongVersion() throws Exception {
        byte width = 6;
        byte height = 8;
        byte[] buffer = new byte[CrosswordBoardStreamHeader.BUFFER_SIZE];
        buffer[0] = (byte)0xCB;
        buffer[1] = (byte)0xFE;
        buffer[2] = CrosswordBoardStreamHeader.VERSION + 1;
        buffer[3] = width;
        buffer[4] = height;

        CrosswordBoardStreamHeader header = new CrosswordBoardStreamHeader(buffer);

        assertTrue(header.parse());
        assertFalse(header.isValid());
        assertEquals(CrosswordBoardStreamHeader.VERSION + 1, header.getVersion());
        assertEquals(width, header.getBoardWidth());
        assertEquals(height, header.getBoardHeight());
    }

    @Test
    public void parse_fail_wrongBuffer() throws Exception {
        byte width = 6;
        byte height = 8;
        byte[] buffer = new byte[CrosswordBoardStreamHeader.BUFFER_SIZE - 1];
        buffer[0] = (byte)0xCB;
        buffer[1] = (byte)0xFE;
        buffer[2] = CrosswordBoardStreamHeader.VERSION;
        buffer[3] = width;
        buffer[4] = height;

        CrosswordBoardStreamHeader header = new CrosswordBoardStreamHeader(buffer);

        assertFalse(header.parse());
    }

    @Test
    public void parse_fail_signature() throws Exception {
        byte[] buffer = new byte[CrosswordBoardStreamHeader.BUFFER_SIZE];
        buffer[0] = (byte)0xFF; //wrong signature
        buffer[1] = (byte)0xFF;
        buffer[2] = CrosswordBoardStreamHeader.VERSION;
        buffer[3] = 11;
        buffer[4] = 12;

        CrosswordBoardStreamHeader header = new CrosswordBoardStreamHeader(buffer);

        assertFalse(header.parse());
    }

    @Test
    public void compose_success() throws  Exception {
        byte width = 6;
        byte height = 8;

        CrosswordBoardStreamHeader header = new CrosswordBoardStreamHeader(width, height);
        byte[] buffer = header.compose();

        assertNotNull(buffer);
        assertEquals((byte)(CrosswordBoardStreamHeader.SIGNATURE >> 8), buffer[0]);
        assertEquals((byte)CrosswordBoardStreamHeader.SIGNATURE , buffer[1]);
        assertEquals(CrosswordBoardStreamHeader.VERSION, buffer[2]);
        assertEquals(width, buffer[3]);
        assertEquals(height, buffer[4]);
    }

}
