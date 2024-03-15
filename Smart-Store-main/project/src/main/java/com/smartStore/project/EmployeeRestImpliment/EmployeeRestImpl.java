package com.smartStore.project.EmployeeRestImpliment;
import com.smartStore.project.EmployeeConstants.EmployeeConstants;
import com.smartStore.project.EmployeeRepository.EmployeeRepository;
import com.smartStore.project.EmployeeRestController.EmployeeRestController;
import com.smartStore.project.EmployeeService.EmployeeService;
import com.smartStore.project.EmployeeUtility.EmployeeUtility;
import com.smartStore.project.EmployeeWrapper.EmployeeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeRestImpl implements EmployeeRestController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public ResponseEntity<String> SignUp(Map<String, String> requestMap) {
        try{

            return employeeService.signUp(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //you're supposed to write this line of code whenever you need to return an error method
        //the best way is to create a utility class to handle this
//        return new ResponseEntity<String>("{\"message\":\"Something Went Wrong!!\"}",
//                HttpStatus.INTERNAL_SERVER_ERROR);

        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        //generic method defined in the utility class instead
        //something went wrong is a constant created on constant class wich will be used throughout the code
    }

    //login page
    @Override
    public ResponseEntity<String> LogIn(Map<String, String> requestMap) {
        try{
            return employeeService.LogIn(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    public ResponseEntity<List<EmployeeWrapper>> getAllEmployee() {
        try{
            return employeeService.getAllEmployee();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<List<EmployeeWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return employeeService.update(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try{
            employeeService.checkToken();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> reuestMap) {
        try {
            return employeeService.changePassword(reuestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgetPassword(Map<String, String> requestMap) {
        try {
            return employeeService.forgetPassword(requestMap);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtility.getResponseUtility(EmployeeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public void deleteEmployee(@PathVariable EmployeeEntity id) {
//
//    }



}
