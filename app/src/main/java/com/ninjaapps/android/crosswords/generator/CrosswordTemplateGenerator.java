package com.ninjaapps.android.crosswords.generator;

import com.ninjaapps.android.crosswords.puzzlemodel.CrosswordTemplate;

public class CrosswordTemplateGenerator {
    private int width, height;

    public CrosswordTemplateGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * generates a template with random separators
     * rules
     * @return
     */
    public CrosswordTemplate generateRandom() {
        CrosswordTemplate template = new CrosswordTemplate(width, height);
        return  template;
    }
}
