package com.ninjaapps.android.crosswords.puzzlemodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ninja on 3/26/2018.
 */

public class CrosswordDefinitionMap {
    private Map<IntPair, CrosswordDefinition> definitions;

    public CrosswordDefinitionMap() {
        definitions = new HashMap<>();
    }

    public void add(int x, int y, CrosswordDefinition definition) {
        definitions.put(new IntPair(x, y), definition);
    }

    public CrosswordDefinition get(int x, int y) {
        return definitions.get(new IntPair(x, y));
    }

    public CrosswordDefinition get(IntPair entry) {
        return definitions.get(entry);
    }

    public Set<IntPair> getAllKeys() {
        return definitions.keySet();
    }
}
