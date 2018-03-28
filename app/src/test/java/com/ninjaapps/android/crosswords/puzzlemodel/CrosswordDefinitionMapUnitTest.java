package com.ninjaapps.android.crosswords.puzzlemodel;

import org.junit.Test;

import java.util.AbstractMap;

import static org.junit.Assert.assertEquals;

public class CrosswordDefinitionMapUnitTest {

    @Test
    public void add_positive() throws Exception {
        CrosswordDefinitionMap map = new CrosswordDefinitionMap();
        CrosswordDefinition def1 = new CrosswordDefinition("def1", "sol1");
        CrosswordDefinition def2 = new CrosswordDefinition("def2", "sol2");
        CrosswordDefinition def3 = new CrosswordDefinition("def3", "sol3");
        CrosswordDefinition def4 = new CrosswordDefinition("def4", "sol4");
        CrosswordDefinition def5 = new CrosswordDefinition("def5", "sol5");

        map.add(1, 1, def1);
        map.add(2, 2, def2);
        map.add(3, 3, def3);
        map.add(4, 4, def4);
        map.add(5, 5, def5);

        assertEquals(5, map.getAllKeys().size());

        assertEquals("def1", map.get(1, 1).getDefinition());
        assertEquals("def2", map.get(2, 2).getDefinition());
        assertEquals("def3", map.get(3, 3).getDefinition());
        assertEquals("def4", map.get(4, 4).getDefinition());
        assertEquals("def5", map.get(5, 5).getDefinition());

        assertEquals("sol1", map.get(1, 1).getSolution());
        assertEquals("sol2", map.get(2, 2).getSolution());
        assertEquals("sol3", map.get(3, 3).getSolution());
        assertEquals("sol4", map.get(4, 4).getSolution());
        assertEquals("sol5", map.get(5, 5).getSolution());
    }

    @Test
    public void add_positive_overwrite() throws Exception {
        CrosswordDefinitionMap map = new CrosswordDefinitionMap();
        CrosswordDefinition def1 = new CrosswordDefinition("def1", "sol1");
        CrosswordDefinition def2 = new CrosswordDefinition("def2", "sol2");
        CrosswordDefinition def3 = new CrosswordDefinition("def3", "sol3");
        CrosswordDefinition def4 = new CrosswordDefinition("def4", "sol4");
        CrosswordDefinition def5 = new CrosswordDefinition("def5", "sol5");

        map.add(1, 1, def1);
        map.add(2, 2, def2);
        map.add(3, 3, def3);

        map.add(1, 1, def4);
        map.add(2, 2, def5);

        assertEquals(3, map.getAllKeys().size());

        assertEquals("def4", map.get(1, 1).getDefinition());
        assertEquals("def5", map.get(2, 2).getDefinition());
        assertEquals("def3", map.get(3, 3).getDefinition());

        assertEquals("sol4", map.get(1, 1).getSolution());
        assertEquals("sol5", map.get(2, 2).getSolution());
        assertEquals("sol3", map.get(3, 3).getSolution());
    }

    @Test
    public void get_positive() throws Exception {
        CrosswordDefinitionMap map = new CrosswordDefinitionMap();
        CrosswordDefinition def1 = new CrosswordDefinition("def1", "sol1");
        CrosswordDefinition def2 = new CrosswordDefinition("def2", "sol2");
        CrosswordDefinition def3 = new CrosswordDefinition("def3", "sol3");

        map.add(1, 1, def1);
        map.add(2, 2, def2);
        map.add(3, 3, def3);

        assertEquals("def1", map.get(new AbstractMap.SimpleEntry<>(1, 1)).getDefinition());
        assertEquals("def2", map.get(new AbstractMap.SimpleEntry<>(2, 2)).getDefinition());
        assertEquals("def3", map.get(new AbstractMap.SimpleEntry<>(3, 3)).getDefinition());

        assertEquals("sol1", map.get(new AbstractMap.SimpleEntry<>(1, 1)).getSolution());
        assertEquals("sol2", map.get(new AbstractMap.SimpleEntry<>(2, 2)).getSolution());
        assertEquals("sol3", map.get(new AbstractMap.SimpleEntry<>(3, 3)).getSolution());
    }
}
