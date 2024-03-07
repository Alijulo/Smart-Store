package com.smartStore.project.EmployeeRepository;

import com.smartStore.project.EmployeeModel.EmployeeEntity;
import com.smartStore.project.EmployeeWrapper.EmployeeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    EmployeeEntity findByEmailId(@Param("emailAddress") String emailAddress);
    List<EmployeeWrapper> getAllEmployee();
}
