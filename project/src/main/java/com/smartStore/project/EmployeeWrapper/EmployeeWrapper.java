package com.smartStore.project.EmployeeWrapper;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EmployeeWrapper {
    private int id;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String emailAddress;
    private String status;

    public EmployeeWrapper(int id, String firstName,
                           String lastName,
                           String idNumber,
                           String emailAddress,
                           String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.emailAddress = emailAddress;
        this.status = status;
    }
}
