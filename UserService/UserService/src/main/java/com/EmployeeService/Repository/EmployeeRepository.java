package com.EmployeeService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EmployeeService.entity.Employee;
import com.UserService.dto.EmployeeDto;





@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByEmail(String email);
  //  Optional<EmployeeDto> findByEmail(String email);
    boolean existsByEmail(String email);  // ✅ Correct keyword
    boolean existsByName(String name);    // ✅ Correct keyword
}
