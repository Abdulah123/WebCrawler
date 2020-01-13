package main;

import crawling.Spider;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SpiderTest {

    private Spider spiderUnderTest;

    @Before
    public void setUp() {
        spiderUnderTest = new Spider();
    }

    @org.junit.Test
    public void testNormalizeNotTheSameUrl() {

        final String link = "link";
        final String domain = "domain";
        final String expectedResult = "result";
        final String result = spiderUnderTest.normalizeUrl(link, domain);
        assertNotSame(expectedResult, result);
    }

    @org.junit.Test
    public void testNormalizeUrl() {
        final String link = "link";
        final String domain = "domain";
        final String expectedResult = "domain/link";
        final String result = spiderUnderTest.normalizeUrl(link, domain);
        assertTrue(result.matches(expectedResult));
    }
}
