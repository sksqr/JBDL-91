package org.gfg.jbdl91.keywords;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueKeywordAnalyzer implements KeywordAnalyzerInterface{

//    private Set<String> dataStore = new HashSet<>();
    private Set<String> dataStore = new LinkedHashSet<>();
    @Override
    public void recordKeyword(String keyword) {
        dataStore.add(keyword);
    }

    @Override
    public List<String> getAllKeywords() {
        return dataStore.stream().toList();
    }
}
