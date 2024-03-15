package com.smartStore.project.EmployeeRegistrationEvent;


import lombok.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class EmployeeRegistrationEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;


    public EmployeeRegistrationEvent(User user, String applicationUrl) {
        super(user);
        this.user=user;
        this.applicationUrl=applicationUrl;
    }
}
