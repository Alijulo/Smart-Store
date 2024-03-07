package com.smartStore.project.JWTUtil;
import com.smartStore.project.EmployeeModel.EmployeeEntity;
import com.smartStore.project.EmployeeRepository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    private com.smartStore.project.EmployeeModel.EmployeeEntity userDetail;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EmployeeEntity user = employeeRepository.findByEmailId(email);
        if(user == null){
            throw new UsernameNotFoundException("Username not found", null);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        // split the role string by comma and create an array of strings
        String[] roles = user.getRole().split(",");
        // create a list of GrantedAuthority objects
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(),
                user.getPassword(),authorities);
    }
    public com.smartStore.project.EmployeeModel.EmployeeEntity getUserDetails(){
        //TODO 1:03
        return userDetail;
    }


}
