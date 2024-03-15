package com.smartStore.project.EmployeeModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

//used for querying data from database table EmployeeEntity
@NamedQuery(name="EmployeeEntity.findByEmailId", query = "select e from EmployeeEntity e " +
        "where e.emailAddress =: emailAddress ")

@NamedQuery(name="EmployeeEntity.getAllEmployee", query = "select new " +
        "com.smartStore.project.EmployeeWrapper.EmployeeWrapper(u.id, u.firstName, u.lastName, u.idNumber, u.emailAddress, u.status) " +
        "from EmployeeEntity u")

//@NamedQuery(name="EmployeeEntity.getAllEmployee", query = "select new " +
//        "com.smartStore.project.EmployeeWrapper.EmployeeWrapper(u.id, u.firstName, u.lastName, u.idNumber, u.emailAddress, u.status) " +
//        "from EmployeeEntity u " +
//        "where u.role =:role")


@NamedQuery(name="EmployeeEntity.updateStatus", query="update EmployeeEntity u set u.status=:status where u.id=:id")

@NamedQuery(name="EmployeeEntity.getAllAdmin", query = "select u.emailAddress from EmployeeEntity u " +
        "where u.role =:role")

@Entity
@Table(name="Employee_Details")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="First_Name")
    private String firstName;

    @Column(name="Last_Name")
    private String lastName;

    @Column(name="Id_Number")
    private String idNumber;

    @Column(name="Email_Address")
    private String emailAddress;

    @Column(name="Password")
    private String password;

    @Column(name="Date_Of_Birth")
    private LocalDate dob;

    @Column(name="Role")
    private String role;

    @Column(name="Status")
    private String status;

    @Transient
    private int age;

    @Column(name="Enabled")
    private boolean isEnabled;

    public int getAge() {
        return Period.between(dob,LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", age=" + age +
                '}';
    }
}
