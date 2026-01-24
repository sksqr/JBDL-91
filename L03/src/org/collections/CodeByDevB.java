package org.collections;

import java.util.List;

public class CodeByDevB {

    public static void main(String[] args) {
        //KeywordAnalyzerInterface keywordAnalyzer = new DefaultKeywordAnalyzer();
        //KeywordAnalyzerInterface keywordAnalyzer = new UniqueKeywordAnalyzer();
        KeywordAnalyzerInterface keywordAnalyzer = new KeywordFreqAnalyzer();

        keywordAnalyzer.recordKeyword("Java");
        keywordAnalyzer.recordKeyword("Html");
        keywordAnalyzer.recordKeyword("C++");
        keywordAnalyzer.recordKeyword("Html");
        keywordAnalyzer.recordKeyword("Python");
        keywordAnalyzer.recordKeyword("Java");
        keywordAnalyzer.recordKeyword("Python");
        keywordAnalyzer.recordKeyword("Python");

        System.out.println(keywordAnalyzer.getAllKeywords());

        System.out.println(keywordAnalyzer.getKeywordWithFreq());

        }
}
/*
Java: 2
Html: 1
 */



class MockKeywordAnalyzer implements KeywordAnalyzerInterface{

    @Override
    public void recordKeyword(String keyword) {

    }

    @Override
    public List<String> getAllKeywords() {
        return null;
    }
}
