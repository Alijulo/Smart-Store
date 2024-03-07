//package com.smartStore.project.JWTSecurity;
//
//import com.smartStore.project.JWTSecurity.EmployeeUserServiceDetails;
//import com.smartStore.project.JWTSecurity.JWTUtility;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///**
// * Extends OncePerRequestFilter: Ensures the filter executes only once per request.
// * Constructor: Injects a UserDetailsService (for user information retrieval) and a JwtTokenUtil (for JWT handling).
// * doFilterInternal:
// * Extracts the JWT token from the request header.
// * Gets the username from the token using jwtTokenUtil.
// * If a valid username is found and no authentication exists in the security context:
// * Loads user details using userDetailsService.
// * Validates the token using jwtTokenUtil.
// * If valid, creates an Authentication object and sets it in the security context.
// * Filter Chain: Proceeds to the next filter in the chain, ensuring subsequent filters see the authenticated user.
// * Remember:
// *
// * Replace JwtTokenUtil with your actual JWT utility class for token validation and extraction.
// * Ensure proper configuration of authentication entry points and exception handling in your SecurityConfig for a complete security setup.
// */
//
//@Component
//public class JwtAuthorizationFilter extends OncePerRequestFilter {
//    private final UserDetailsService userDetailsService;
//    @Autowired
//    private final JWTUtility jwtUtility;
//    @Autowired
//    EmployeeUserServiceDetails employeeUserServiceDetails;
//
//    public JwtAuthorizationFilter(UserDetailsService userDetailsService, JWTUtility jwtUtility) {
//        this.userDetailsService = userDetailsService;
//        this.jwtUtility = jwtUtility;}
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwtToken = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwtToken = authorizationHeader.substring(7);
//            username = jwtUtility.extractUsername(jwtToken);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = employeeUserServiceDetails.loadUserByUsername(username);
//
//            if (jwtUtility.validateToken(jwtToken, userDetails)) {
//                UsernamePasswordAuthenticationToken authenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    }
