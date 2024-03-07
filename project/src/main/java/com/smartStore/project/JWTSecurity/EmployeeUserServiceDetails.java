//package com.smartStore.project.JWTSecurity;
//
//import com.smartStore.project.EmployeeModel.EmployeeEntity;
//import com.smartStore.project.EmployeeRepository.EmployeeRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//@Slf4j
//@Service
//public class EmployeeUserServiceDetails implements UserDetailsService {
//    @Autowired
//    EmployeeRepository employeeRepository;
//
//    private com.smartStore.project.EmployeeModel.EmployeeEntity userDetails;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("inside loadUserByUsername{}",username);
//        userDetails=employeeRepository.findByEmailId(username);
//        if(!Objects.isNull(userDetails))
//        return new User(userDetails.getEmailAddress(),userDetails.getPassword(),new ArrayList<>());
//        else
//            throw new UsernameNotFoundException("User not found");
//    }
//
//    public com.smartStore.project.EmployeeModel.EmployeeEntity getEmployeeDetails(){
//        return userDetails;
//    }
//}
