package com.smartStore.project.EmployeeEvents;

import com.smartStore.project.EmployeeModel.EmployeeEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private EmployeeEntity employee;
    private String applicationUrl;

    public RegistrationCompleteEvent(EmployeeEntity employee, String applicationUrl) {
        super(applicationUrl);
    }
}
