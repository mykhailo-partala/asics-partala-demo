package com.asics.demo.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OOPWordSorter implements WordSorter {

    @Override
    public List<String> sort(List<String> words) {
        WordSorter.validateWords(words);
        List<Word> wordList = new ArrayList<>();
        for (String word : words) {
            wordList.add(new Word(word));
        }

        wordList.sort(Comparator.comparingInt(Word::getValue));

        List<String> sortedWords = new ArrayList<>();
        for (Word word : wordList) {
            sortedWords.add(word.getText());
        }
        return sortedWords;
    }

}
