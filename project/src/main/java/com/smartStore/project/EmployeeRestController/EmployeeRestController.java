package com.smartStore.project.EmployeeRestController;

import com.smartStore.project.EmployeeModel.EmployeeEntity;
import com.smartStore.project.EmployeeWrapper.EmployeeWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/employee")//base url when user try to access employee page
public interface EmployeeRestController {
    @PostMapping(path = "/signup")
    public ResponseEntity<String> SignUp(@RequestBody()
                                         Map<String, String> requestMap);
    @GetMapping("/hello")
    public default String hello(){
        return "hello";
    }

    @PostMapping("/login")
    public ResponseEntity<String> LogIn(@RequestBody () Map<String,String> requestMap);

//    @DeleteMapping("/employee/delete/{id}")
//    public void deleteEmployee(@PathVariable EmployeeEntity id);

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeWrapper>> getAllEmployee();

}