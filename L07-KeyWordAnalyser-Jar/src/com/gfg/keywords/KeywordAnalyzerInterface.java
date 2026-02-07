package com.gfg.keywords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface KeywordAnalyzerInterface {

    void recordKeyword(String keyword);

    List<String> getAllKeywords();

    default Map<String,Integer> getKeywordWithFreq(){
        return new HashMap<>();
    }
}
