package com.ninjaapps.android.crosswords.puzzlemodel;

import android.util.Pair;

import java.util.ArrayList;
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

    public void add(byte colRow, byte index, CrosswordDefinition definition) {
        definitions.put(Pair.create(colRow, index), definition);
    }

    public CrosswordDefinition get(byte colRow, byte index) {
        return definitions.get(Pair.create(colRow, index));
    }

    public Set<Pair<Byte, Byte>> getAllKeys() {
        return definitions.keySet();
    }
}
