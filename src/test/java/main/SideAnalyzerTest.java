package main;

import crawling.SideAnalyzer;
import org.junit.Before;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SideAnalyzerTest {

    private SideAnalyzer sideAnalyzerUnderTest;

    @Before
    public void setUp() {
        sideAnalyzerUnderTest = new SideAnalyzer();
    }

    @org.junit.Test
    public void testRemoveStopWords() {
        final String description = "result and";
        final String expectedResult = "result";
        final List<String> result = sideAnalyzerUnderTest.removeStopWords(description);

        assertEquals(expectedResult, result.get(0));
    }

    @org.junit.Test
    public void testCountWords() {

        final Map<String, Integer> input = new HashMap<>();
        final Map<String, Integer> expectedResult = new HashMap<>();
        final Map<String, Integer> result = sideAnalyzerUnderTest.countWords(expectedResult, input);
        assertEquals(expectedResult, result);

    }

    @org.junit.Test
    public void testMergeAndCount() {

        final List<String> input = new LinkedList<>();
        final Map<String, Integer> expectedResult = new HashMap<>();
        final Map<String, Integer> result = sideAnalyzerUnderTest.mergeAndCount(input);
        assertEquals(expectedResult, result);
    }

    @org.junit.Test
    public void testSort() {

        final Map<String, Integer> result = new HashMap<>();
        final List<Map.Entry<String, Integer>> expectedResult = Arrays.asList();
        final List<Map.Entry<String, Integer>> finalResult = sideAnalyzerUnderTest.sort(result);
        assertEquals(expectedResult, finalResult);
    }

    @org.junit.Test
    public void testCall() {

        final Map<String, Integer> fis = new HashMap<>();
        final String jobs = "jobs";
        final Map<String, Integer> result = sideAnalyzerUnderTest.call(fis, jobs);
        assertEquals(fis, result);
    }


}