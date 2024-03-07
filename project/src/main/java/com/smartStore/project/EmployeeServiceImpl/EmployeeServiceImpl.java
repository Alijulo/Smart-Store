package com.smartStore.project.EmployeeServiceImpl;
import com.smartStore.project.EmployeeConstants.EmployeeConstants;
import com.smartStore.project.EmployeeModel.EmployeeEntity;
import com.smartStore.project.EmployeeRepository.EmployeeRepository;
import com.smartStore.project.EmployeeService.EmployeeService;
import com.smartStore.project.EmployeeUtility.EmployeeUtility;
import com.smartStore.project.EmployeeWrapper.EmployeeWrapper;
import com.smartStore.project.JWTUtil.JwtFilter;
import com.smartStore.project.JWTUtil.JwtUtil;
import com.smartStore.project.JWTUtil.UserDetailsServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j //used for logging methods
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> resquestMap) {

        log.info("inside SignUp {}",resquestMap);
        try {
            if (validateSignUp(resquestMap)) {
                EmployeeEntity employee = employeeRepository.findByEmailId(resquestMap.get("emailAddress"));
                if (Objects.isNull(employee)) {
                    employeeRepository.save(setEmployeeMap(resquestMap));
                    return EmployeeUtility.getResponseUtility("Successfully Registered.", HttpStatus.OK);

                } else {
                    return EmployeeUtility.getResponseUtility("Employee with the same email Already Exist", HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return EmployeeUtility.getResponseUtility(EmployeeConstants.INVALID_DATA,
                        HttpStatus.BAD_REQUEST);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUp(Map<String,String> requestMap){
       if( requestMap.containsKey("firstName") &&
               requestMap.containsKey("lastName") &&
               requestMap.containsKey("idNumber") &&
               requestMap.containsKey("emailAddress") &&
                requestMap.containsKey("password")){
           return true;
       }
       return false;
    }

    //method for setting new employee details
    private EmployeeEntity setEmployeeMap(Map<String,String > requestMap){
        EmployeeEntity employeeEntity =new EmployeeEntity();
        employeeEntity.setFirstName(requestMap.get("firstName"));
        employeeEntity.setLastName(requestMap.get("lastName"));
        employeeEntity.setIdNumber(requestMap.get("idNumber"));
        employeeEntity.setEmailAddress(requestMap.get("emailAddress"));
        //employeeEntity.setPassword(requestMap.get("password"));
        employeeEntity.setPassword(new BCryptPasswordEncoder().encode(requestMap.get("password")));
        return employeeEntity;
    }


//    @Override
//    public ResponseEntity<String> LogIn(Map<String, String> requestMap) {
//        log.info("Inside login");
//        try {
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(requestMap.get("emailAddress"), requestMap.get("password"))
//            );
//
//            if (auth.isAuthenticated()) {
//                userDetailsService.getUserDetails();  //TODO adding Status option
//                return new ResponseEntity<String>("{\"token\":\"" +
//                        jwtUtil.generateToken(userDetailsService.getUserDetails().getEmailAddress()) + "\"}", HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  null;

//    }

    @Override
public ResponseEntity<String> LogIn (Map < String, String > requestMap){
    log.info("Login attempt for email: {}", requestMap.get("emailAddress"));

    // Validate request body
    if (requestMap == null || !requestMap.containsKey("emailAddress") || !requestMap.containsKey("password")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields in request body");
    }

    try {
        String emailAddress = requestMap.get("emailAddress");
        String password = requestMap.get("password");
        // Load user details (avoid logging username)
        UserDetails userDetails = userDetailsService.loadUserByUsername(emailAddress);

        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailAddress, password));

        // Generate JWT token
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return successful response with generic message
        return new ResponseEntity<>(jwt, HttpStatus.CREATED);

    }
    catch (UsernameNotFoundException e) {
        log.warn("Login failed for email: {}");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
    catch (BadCredentialsException e) {
        log.warn("Login failed for email: {}");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
    catch (Exception e) {
        log.error("An error occurred during authentication", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    //fetching all employees from the database
    @Override
    public ResponseEntity<List<EmployeeWrapper>> getAllEmployee() {
        log.error("An error occurred in getAllEmployee ");

        try{
        if (jwtFilter.isAdmin()){

            Query query = entityManager.createNamedQuery("EmployeeEntity.getAllEmployee", EmployeeWrapper.class);
            List<EmployeeWrapper> results = query.getResultList();

            return new ResponseEntity<>(results, HttpStatus.OK);

//            Query query = entityManager.createNamedQuery("EmployeeEntity.getAllEmployee", EmployeeWrapper.class);
//            query.setParameter("role", "admin"); // replace "admin" with the actual role value
//            List<EmployeeWrapper> results = query.getResultList();
//
//            return new ResponseEntity<>(results, HttpStatus.OK);

            //return new ResponseEntity<>(employeeRepository.getAllEmployee(),HttpStatus.OK);
        }
        else {

            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}




