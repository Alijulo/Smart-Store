package com.smartStore.project.EmployeeRestImpliment;

import com.smartStore.project.EmployeeConstants.EmployeeConstants;
import com.smartStore.project.EmployeeRestController.EmployeeSignUpController;
import com.smartStore.project.EmployeeServiceImpl.EmployeeSignUpServiceImpl;
import com.smartStore.project.EmployeeUtility.EmployeeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class EmployeeSignUpControllerImpl implements EmployeeSignUpController {
    @Autowired
    EmployeeSignUpServiceImpl employeeSignUpService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return employeeSignUpService.signUp(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
