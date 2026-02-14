package com.example.L09_Logging_MVC_Annotation_demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "gfg.service",name = "enable", havingValue = "true")
public class GFGService {

    public List<String> getLectures(){
        return Arrays.asList("lec-01","lec-02");
    }

}
