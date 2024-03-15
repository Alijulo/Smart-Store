package com.smartStore.project.EmployeeService;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EmployeeSignUpService {
    public ResponseEntity<String> signUp(Map<String,String> resquestMap);
}
