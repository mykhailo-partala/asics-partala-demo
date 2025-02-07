package com.asics.demo.sort;

import java.util.Comparator;
import java.util.List;

public class FunctionalWordSorter implements WordSorter {
    @Override
    public List<String> sort(List<String> words) {
        WordSorter.validateWords(words);
        return words.stream()
                .sorted(Comparator.comparingInt(WordSorter::calculateWordValue))
                .toList();
    }
}
