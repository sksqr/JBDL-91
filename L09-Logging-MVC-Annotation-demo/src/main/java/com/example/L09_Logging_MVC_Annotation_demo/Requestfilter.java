package com.example.L09_Logging_MVC_Annotation_demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class Requestfilter extends HttpFilter {

    private static Logger LOGGER = LoggerFactory.getLogger(Requestfilter.class);

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        MDC.put("requestId",request.getHeader("requestId"));
        LOGGER.info("Processing Filter Before Controller");
        filterChain.doFilter(request,response);// Design Pattern: Chain of Responsibility
        LOGGER.info("Processing Filter After Controller");
        MDC.clear();
    }


    }
