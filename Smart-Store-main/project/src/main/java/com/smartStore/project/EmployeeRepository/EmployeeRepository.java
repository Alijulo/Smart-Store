package com.smartStore.project.EmployeeRepository;

import com.smartStore.project.EmployeeModel.EmployeeEntity;
import com.smartStore.project.EmployeeWrapper.EmployeeWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    EmployeeEntity findByEmailId(@Param("emailAddress") String emailAddress);
    List<EmployeeWrapper> getAllEmployee();

    @Transactional //if you are querying for updates use these two annotations
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    EmployeeEntity findByEmailAddress(String emailAddress);

    List<String>getAllAdmin();
}
