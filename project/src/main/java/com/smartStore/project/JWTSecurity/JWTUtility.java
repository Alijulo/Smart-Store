//package com.smartStore.project.JWTSecurity;
//
//import com.smartStore.project.EmployeeModel.EmployeeEntity;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//public class JWTUtility {
//    EmployeeEntity employeeEntity=new EmployeeEntity();
//    private final String secretKey="Ali@123";
//
//    public String extractUsername(String token){
//
//        return extractClaims(token, Claims::getSubject);
//   }
//
//
//   public Date extractExpiration(String token){
//        return extractClaims(token,Claims::getExpiration);
//   }
//    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public Claims extractAllClaims(String token) {
//        return (Claims) Jwts.parser().setSigningKey(secretKey).parse(token).getBody();
//    }
//
//
//    //using 0.9 version of jjwt dependency
////
////    public Claims extractAllClaims(String token){
////
////        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
////    }
//
//    public Boolean isTokenExpared(String token){
//        return extractExpiration(token).before(new Date());
//    }
//
//    public String generateToken(String username,String role){
//        Map<String, Object> claims=new HashMap<>();
//        claims.put("role",role);
//        return createToken(claims,username);
//
//    }
//
//    private String createToken(Map<String,Object> claims, String subject){
//        return Jwts
//                .builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
//                .signWith(SignatureAlgorithm.HS256,secretKey).compact();
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails){
//        final String username=extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpared(token));
//    }
//}
