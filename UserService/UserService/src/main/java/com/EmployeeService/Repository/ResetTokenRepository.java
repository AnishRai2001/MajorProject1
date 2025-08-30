package com.EmployeeService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EmployeeService.entity.Employee;
import com.EmployeeService.entity.ResetToken;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {
    Optional<ResetToken> findByToken(String token);
    void deleteByEmployee(Employee employee);  // use 'employee', not 'user'
}
