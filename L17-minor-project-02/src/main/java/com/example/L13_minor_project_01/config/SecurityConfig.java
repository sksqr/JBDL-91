package com.example.L13_minor_project_01.config;

import com.example.L13_minor_project_01.entity.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name())
                            .requestMatchers("/api/customer/**").hasAnyAuthority(UserRole.CUSTOMER.name())
                            .requestMatchers("/api/seller/**").hasAnyAuthority(UserRole.SELLER.name())
                            .requestMatchers("/public/**","/product-images/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

//    public static void main(String[] args) {
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("123456"));
//    }
}


