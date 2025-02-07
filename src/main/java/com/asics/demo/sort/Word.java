package com.asics.demo.sort;

class Word {
    private final String text;
    private final int value;

    public Word(String text) {
        this.text = text;
        this.value = WordSorter.calculateWordValue(text);
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
