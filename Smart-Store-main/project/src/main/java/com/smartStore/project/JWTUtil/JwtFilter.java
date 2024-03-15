package com.smartStore.project.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    String role;
    String userName=null;
    String jwtToken = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");


        if(header != null && header.startsWith("Bearer ")){
            jwtToken = header.substring(7);

            try{
                userName = jwtUtil.extractUserName(jwtToken);
                role = jwtUtil.extractRole(jwtToken);

            }catch(IllegalArgumentException e){
                System.out.println("Unable to get JWTToken");
            }catch (ExpiredJwtException e){
                System.out.println("Token Expired");
            }
        }else{
            System.out.println("JWT token does not start with bearer");
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if(jwtUtil.isTokenValid(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);

    }


    public boolean isAdmin(){
        System.out.println("the user is: "+role);

        return "admin".equalsIgnoreCase(role);//role.equalsIgnoreCase("admin");
    }

    public boolean isUser(){
        return role.equalsIgnoreCase("user");
    }

    public String getCurrentUser(){

        return userName;
    }
}
