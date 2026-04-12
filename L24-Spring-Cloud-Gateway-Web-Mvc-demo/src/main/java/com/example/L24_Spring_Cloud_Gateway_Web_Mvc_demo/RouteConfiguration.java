package com.example.L24_Spring_Cloud_Gateway_Web_Mvc_demo;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
class RouteConfiguration {

//    @Bean
//    public RouterFunction<ServerResponse> gatewayRouterFunctionsAddReqHeader() {
//        return route("add_request_header_route")
//                .GET("/wallet-service/**", http())
//                .before(uri("lb://wallet-service"))
////                .before(uri("http://localhost:8081"))
//                .before(addRequestHeader("requestId", UUID.randomUUID().toString())).build();
//    }
}