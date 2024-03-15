package com.smartStore.project.EmployeeServiceImpl;

import com.smartStore.project.EmailUtil.EmailUtils;
import com.smartStore.project.EmployeeConstants.EmployeeConstants;
import com.smartStore.project.EmployeeModel.EmployeeEntity;
import com.smartStore.project.EmployeeRepository.EmployeeRepository;
import com.smartStore.project.EmployeeService.EmployeeSignUpService;
import com.smartStore.project.EmployeeUtility.EmployeeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class EmployeeSignUpServiceImpl implements EmployeeSignUpService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> resquestMap) {
        try{
            EmployeeEntity employee=employeeRepository.findByEmailAddress("emailAddress");
            if(Objects.isNull(employee)){
                employeeRepository.save(setEmployeeMap(resquestMap));
                return EmployeeUtility.getResponseUtility("Success! please check your email to complete your registration",HttpStatus.OK);
            }
            return EmployeeUtility.getResponseUtility("User with the same email already exist",HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateSignUp(Map<String,String> requestMap){
        if( requestMap.containsKey("firstName") &&
                requestMap.containsKey("lastName") &&
                requestMap.containsKey("idNumber") &&
                requestMap.containsKey("emailAddress") &&
                requestMap.containsKey("password")&&
                requestMap.containsKey("role")){
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
        employeeEntity.setRole(requestMap.get("role"));
        return employeeEntity;
    }

}
