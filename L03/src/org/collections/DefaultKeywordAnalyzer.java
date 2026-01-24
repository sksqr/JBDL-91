package org.collections;

import java.util.ArrayList;
import java.util.List;

// Code by dev A
public class DefaultKeywordAnalyzer implements KeywordAnalyzerInterface{

    private List<String> dataStore = new ArrayList<>();
    @Override
    public void recordKeyword(String keyword) {
        dataStore.add(keyword);
    }

    @Override
    public List<String> getAllKeywords() {
        return dataStore;
    }
}
