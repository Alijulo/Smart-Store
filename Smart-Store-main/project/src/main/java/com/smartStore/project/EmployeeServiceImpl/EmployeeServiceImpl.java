package com.smartStore.project.EmployeeServiceImpl;
import com.smartStore.project.EmailUtil.EmailUtils;
import com.smartStore.project.EmployeeConstants.EmployeeConstants;
import com.smartStore.project.EmployeeModel.EmployeeEntity;
import com.smartStore.project.EmployeeRegistrationEvent.EmployeeRegistrationEvent;
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
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    EmailUtils emailUtils;

    @PersistenceContext
    private EntityManager entityManager;

    private  ApplicationEventPublisher applicationEventPublisher;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        employeeEntity.setPassword(new BCryptPasswordEncoder().encode(requestMap.get("password")));
        return employeeEntity;
    }


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

            //will be used to fetch user details

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

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){

                Optional<EmployeeEntity> optional= employeeRepository.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    employeeRepository.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmailAddress(),allAdminQuery());
                    return EmployeeUtility.getResponseUtility("Employee Status Updated successfully",HttpStatus.OK);
                }
                else{
                    return EmployeeUtility.getResponseUtility("User id doesn,t exist",HttpStatus.OK);
                }
            }
            else{
                return EmployeeUtility.getResponseUtility(EmployeeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> checkToken() {
        return EmployeeUtility.getResponseUtility("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            EmployeeEntity employeeObj=employeeRepository.findByEmailAddress(jwtFilter.getCurrentUser());
            if(!employeeObj.equals(null)){
                if(passwordEncoder.matches(requestMap.get("oldPassword"),employeeObj.getPassword())){
                    employeeObj.setPassword(new BCryptPasswordEncoder().encode(requestMap.get("newPassword")));
                    employeeRepository.save(employeeObj);
                    return EmployeeUtility.getResponseUtility("Password Updated Successfully",HttpStatus.OK);
                }
                return EmployeeUtility.getResponseUtility("Incorrect Old Password",HttpStatus.BAD_REQUEST);

            }
            return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgetPassword(Map<String, String> requestMap) {
        try {
            EmployeeEntity employee=employeeRepository.findByEmailAddress(requestMap.get("emailAddress"));
            if(!Objects.isNull(employee) && Strings.isNotEmpty(employee.getEmailAddress())){
                emailUtils.forgetMail(employee.getEmailAddress(),"credentials by SmartStore Management",employee.getPassword());
            }
            return EmployeeUtility.getResponseUtility("Check your email credentials",HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAllAdmin(String status, String employee, List<String> allAdmin) {
        System.out.println("this is the current user: "+jwtFilter.getCurrentUser());
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendMessage(jwtFilter.getCurrentUser(),"Account Approved", "EMPLOYEE:- "+employee+"\n is approved by\nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);
        }
        else {
            emailUtils.sendMessage(jwtFilter.getCurrentUser(),"Account Disabled", "EMPLOYEE:- "+employee+"\n is Disabled by\nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);
        }
    }

    private List<String> allAdminQuery(){
        Query query = entityManager.createNamedQuery("EmployeeEntity.getAllAdmin", EmployeeEntity.class);
        query.setParameter("role", "admin"); // Replace "admin" with the actual role value
        List<String> results = query.getResultList();

//        System.out.println("this are the admins: "+results);

        return results;
    }


}




