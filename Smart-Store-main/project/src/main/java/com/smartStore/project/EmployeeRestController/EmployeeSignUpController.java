package com.smartStore.project.EmployeeRestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/employee")//base url when user try to access employee page
public interface EmployeeSignUpController {
    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody Map<String,String> requestMap);
    
}
