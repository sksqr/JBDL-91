package com.client;

import com.gfg.keywords.KeywordAnalyzerInterface;
import com.gfg.keywords.NewKeywordAnalyser;
import com.gfg.keywords.UniqueKeywordAnalyzer;

public class EcommSearchService {

    public static void main(String[] args) {

//        KeywordAnalyzerInterface keywordAnalyzer = new NewKeywordAnalyser();
        KeywordAnalyzerInterface keywordAnalyzer = new UniqueKeywordAnalyzer();
        keywordAnalyzer.recordKeyword("Pen");
        keywordAnalyzer.recordKeyword("Mouse");
        keywordAnalyzer.recordKeyword("Pen");
        System.out.println(keywordAnalyzer.getAllKeywords());
    }
}
