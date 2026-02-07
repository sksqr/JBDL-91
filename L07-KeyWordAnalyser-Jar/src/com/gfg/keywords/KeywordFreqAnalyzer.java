package com.gfg.keywords;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KeywordFreqAnalyzer implements KeywordAnalyzerInterface{

    //private Map<String,Integer> dataStore = new HashMap<>();
    private Map<String,Integer> dataStore = new LinkedHashMap<>();
    @Override
    public void recordKeyword(String keyword) {
        if(dataStore.get(keyword) == null){
            dataStore.put(keyword,1);
        }
        else{
            dataStore.put(keyword,dataStore.get(keyword)+1);
        }
    }

    @Override
    public List<String> getAllKeywords() {
        return dataStore.keySet().stream().toList();
    }

    @Override
    public Map<String, Integer> getKeywordWithFreq() {
        return dataStore;
    }
}
