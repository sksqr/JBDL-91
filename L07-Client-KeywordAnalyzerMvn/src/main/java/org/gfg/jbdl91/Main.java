package org.gfg.jbdl91;

import org.gfg.jbdl91.keywords.KeywordAnalyzerInterface;
import org.gfg.jbdl91.keywords.LatestKeywordAnalyzer;
import org.gfg.jbdl91.keywords.LatestUniqueKeywordAnalyzer;
import org.gfg.jbdl91.keywords.UniqueKeywordAnalyzer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        KeywordAnalyzerInterface keywordAnalyzer = new LatestUniqueKeywordAnalyzer();
    }
}