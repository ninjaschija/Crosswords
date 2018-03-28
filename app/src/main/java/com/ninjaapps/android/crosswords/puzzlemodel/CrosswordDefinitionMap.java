package com.ninjaapps.android.crosswords.puzzlemodel;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ninja on 3/26/2018.
 */

public class CrosswordDefinitionMap {
    //Use Map.Entry as replacement for Pair class in android.util
    private Map<Map.Entry<Integer, Integer>, CrosswordDefinition> definitions;

    public CrosswordDefinitionMap() {
        definitions = new HashMap<>();
    }

    public void add(int x, int y, CrosswordDefinition definition) {
        definitions.put(new AbstractMap.SimpleEntry<>(x, y), definition);
    }

    public CrosswordDefinition get(int x, int y) {
        return definitions.get(new AbstractMap.SimpleEntry<>(x, y));
    }

    public CrosswordDefinition get(Map.Entry<Integer, Integer> entry) {
        return definitions.get(entry);
    }

    public Set<Map.Entry<Integer, Integer>> getAllKeys() {
        return definitions.keySet();
    }
}
