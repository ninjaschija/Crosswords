package com.ninjaapps.android.crosswords.puzzlemodel;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ninja on 3/26/2018.
 */

public class CrosswordDefinitionMap {
    private Map<Pair<Byte, Byte>, CrosswordDefinition> definitions;

    public CrosswordDefinitionMap() {
        definitions = new HashMap<>();
    }

    public void add(byte x, byte y, CrosswordDefinition definition) {
        definitions.put(Pair.create(x, y), definition);
    }

    public CrosswordDefinition get(byte x, byte y) {
        return definitions.get(Pair.create(x, y));
    }

    public Set<Pair<Byte, Byte>> getAllKeys() {
        return definitions.keySet();
    }
}
