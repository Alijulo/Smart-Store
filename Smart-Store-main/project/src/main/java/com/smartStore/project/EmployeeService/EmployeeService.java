package com.smartStore.project.EmployeeService;

import com.smartStore.project.EmployeeWrapper.EmployeeWrapper;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface EmployeeService {
    public ResponseEntity<String>signUp(Map<String,String> resquestMap);
    public ResponseEntity<String>LogIn(Map<String,String> resquestMap);
    ResponseEntity<List<EmployeeWrapper>> getAllEmployee();
    ResponseEntity<String> update(Map<String,String> requestMap);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(Map<String,String> requestMap);
    ResponseEntity<String> forgetPassword(Map<String,String> requestMap);

}
