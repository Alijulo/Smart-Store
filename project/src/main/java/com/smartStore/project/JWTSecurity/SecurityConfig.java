//package com.smartStore.project.JWTSecurity;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@EnableWebSecurity
//public class SecurityConfig  {
//
//    @Autowired
//    JwtAuthorizationFilter jwtAuthorizationFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/hello").permitAll())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/home").authenticated())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//
//
//    }
//
//
//}
