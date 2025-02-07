package com.asics.demo.sort;

import java.util.List;

public class SortDemoApplication {

    private static final List<String> WORDS_TEST_1 = List.of("A", "Zebra", "abc", "XYZ", "AAA");
    // Expected: [A, AAA, abc, Zebra, XYZ]

    private static final List<String> WORDS_TEST_2 = List.of("", "123", "hello!");
    // Expected: IllegalArgumentException

    private static final List<String> WORDS_TEST_3 = List.of("ABC", "BCA", "ACB");
    // Expected: [ABC, BCA, ACB]

    private static final List<String> WORDS_TEST_4 = List.of();
    // Expected: []

    private static final List<String> WORDS_TEST_5 = List.of("A");
    // Expected: [A]

    private static final List<String> WORDS_TEST_6 = List.of("aBc", "XyZ");
    // Expected: [aBc, XyZ] (same values as ABC, XYZ)

    public static void main(String[] args) {
        WordSorter wordSorter = new FunctionalWordSorter();

        runTest("Test 1: Basic Sorting", WORDS_TEST_1, wordSorter);
        runTest("Test 2: Invalid Words (should fail)", WORDS_TEST_2, wordSorter);
        runTest("Test 3: Same Value Words", WORDS_TEST_3, wordSorter);
        runTest("Test 4: Empty List", WORDS_TEST_4, wordSorter);
        runTest("Test 5: Single Word", WORDS_TEST_5, wordSorter);
        runTest("Test 6: Mixed Case", WORDS_TEST_6, wordSorter);
    }

    private static void runTest(String testName, List<String> words, WordSorter sorter) {
        System.out.println(testName + " - Input: " + words);
        try {
            List<String> sortedWords = sorter.sort(words);
            System.out.println("Sorted Output: " + sortedWords);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println();
    }

}
