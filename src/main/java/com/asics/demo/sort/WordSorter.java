package com.asics.demo.sort;

import java.util.List;

public interface WordSorter {

    List<String> sort(List<String> words);

    static void validateWords(List<String> words) {
        for (String word : words) {
            if (word == null || word.isEmpty()) {
                throw new IllegalArgumentException(WordConstants.ERROR_EMPTY_WORD);
            }
            if (!word.matches("[a-zA-Z]+")) {
                throw new IllegalArgumentException(WordConstants.ERROR_INVALID_CHARACTERS);
            }
        }
    }

    static int calculateWordValue(String word) {
        return word.toUpperCase()
                .chars()
                .map(c -> c - 'A' + 1)
                .sum();
    }
}
