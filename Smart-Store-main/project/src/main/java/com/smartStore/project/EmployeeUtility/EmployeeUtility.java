package com.smartStore.project.EmployeeUtility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EmployeeUtility {
    //Generic methods which can be used in all classes

    private EmployeeUtility(){

    }

    public static ResponseEntity<String> getResponseUtility(String responseMessage,
                                                            HttpStatus httpStatus){

        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}",
                httpStatus);
    }

}
