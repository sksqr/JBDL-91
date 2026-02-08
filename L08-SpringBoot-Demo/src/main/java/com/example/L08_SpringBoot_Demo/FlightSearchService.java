package com.example.L08_SpringBoot_Demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FlightSearchService {

    @Value("${indigo.url}")
    private String indigoUrl;

    public String callIndigoAPIForData(){
        /*
        API Call to indigo server.
        1. Developer Machine: dev-testing-API
        2. Stage Env: stage-API (Testing)
        3. live ENV: prod indigo API
         */
        System.out.println(indigoUrl);
        return indigoUrl;
    }

}
