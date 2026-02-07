package org.gfg.jbdl91.keywords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniqueKeywordAnalyzerTest {

    private UniqueKeywordAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new UniqueKeywordAnalyzer();
    }

    @Test
    void testRecordSingleKeyword() {
        analyzer.recordKeyword("java");

        List<String> keywords = analyzer.getAllKeywords();

        assertEquals(1, keywords.size());
        assertEquals("java", keywords.get(0));
    }

    @Test
    void testDuplicateKeywordsAreNotStored() {
        analyzer.recordKeyword("java");
        analyzer.recordKeyword("java");
        analyzer.recordKeyword("java");

        List<String> keywords = analyzer.getAllKeywords();

        assertEquals(1, keywords.size());
        assertEquals("java", keywords.get(0));
    }

    @Test
    void testInsertionOrderIsMaintained() {
        analyzer.recordKeyword("java");
        analyzer.recordKeyword("spring");
        analyzer.recordKeyword("hibernate");

        List<String> keywords = analyzer.getAllKeywords();

        assertEquals(List.of("java", "spring", "hibernate"), keywords);
    }

    @Test
    void testGetAllKeywordsWhenEmpty() {
        List<String> keywords = analyzer.getAllKeywords();

        assertNotNull(keywords);
        assertTrue(keywords.isEmpty());
    }
}
