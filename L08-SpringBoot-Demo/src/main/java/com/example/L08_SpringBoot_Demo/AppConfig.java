package com.example.L08_SpringBoot_Demo;

import org.gfg.jbdl91.keywords.KeywordAnalyzerInterface;
import org.gfg.jbdl91.keywords.UniqueKeywordAnalyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public KeywordAnalyzerInterface keywordAnalyzer(){
        return new UniqueKeywordAnalyzer();
    }
}
