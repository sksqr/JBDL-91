package com.example.L10_Annotation_RestFul_AOP_demo;

import org.gfg.jbdl91.keywords.KeywordAnalyzerInterface;
import org.gfg.jbdl91.keywords.KeywordFreqAnalyzer;
import org.gfg.jbdl91.keywords.UniqueKeywordAnalyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    @Bean("uniqueKeywordAnalyzer")
    public KeywordAnalyzerInterface getKeywordAnalyzer(){
        return new UniqueKeywordAnalyzer();
    }

    @Primary
    @Bean("keywordFreqAnalyzer")
    public KeywordAnalyzerInterface getKeywordFreqAnalyzer(){
        return new KeywordFreqAnalyzer();
    }
}
