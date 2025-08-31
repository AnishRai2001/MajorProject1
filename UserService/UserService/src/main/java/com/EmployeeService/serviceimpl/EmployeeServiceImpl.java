package com.EmployeeService.serviceimpl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.EmployeeService.Repository.EmployeeRepository;
import com.EmployeeService.Repository.ResetTokenRepository;
import com.EmployeeService.Repository.VerificationTokenRepository;
import com.EmployeeService.entity.Employee;
import com.EmployeeService.entity.ResetToken;
import com.EmployeeService.entity.VerificationToken;
import com.EmployeeService.exception.EmailAlreadyExistsException;
import com.EmployeeService.exception.NameAlreadyExistsException;
import com.EmployeeService.service.EmployeeService;
import com.UserService.dto.EmployeeDto;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository tokenRepo;

    @Autowired
    private ResetTokenRepository resetRepo;

    // ========================= REGISTER EMPLOYEE =========================
    @Override
    public Employee RegisterEmployee(EmployeeDto employeeDto) {

        // Check if email exists
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new EmailAlreadyExistsException("Employee email already in use");
        }

        // Check if name exists
        if (employeeRepository.existsByName(employeeDto.getName())) {
            throw new NameAlreadyExistsException("Employee name already in use");
        }

        // Copy DTO → Entity
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        // Save Employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Create Verification Token
        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(24));
        token.setEmployee(savedEmployee);
        tokenRepo.save(token);

        // TODO: Send verification email with token.getToken()

        return savedEmployee;
    }

    // ========================= VERIFY EMAIL =========================
    @Override
    @Transactional
    public boolean verifyEmail(String tokenStr) {
        VerificationToken token = tokenRepo.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token expired");

        Employee employee = token.getEmployee();
        employee.setEnabled(true);  // make sure Employee has 'verified' field
        employeeRepository.save(employee);

        tokenRepo.deleteByEmployee(employee);

        return true;
    }

    // ========================= CREATE RESET TOKEN =========================
    @Override
    public void createResetToken(String email) {
    	   Employee employee = employeeRepository.findByEmail(email)
    	            .orElseThrow(() -> new RuntimeException("Employee not found"));

        ResetToken resetToken = new ResetToken();
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(2));
        resetToken.setEmployee(employee);
        resetRepo.save(resetToken);

        // TODO: Send password reset email with resetToken.getToken()
    }

    // ========================= RESET PASSWORD =========================
    @Override
    public void resetPassword(String tokenStr, String newPassword) {
        ResetToken token = resetRepo.findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token expired");

        Employee employee = token.getEmployee();
        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);

        resetRepo.deleteByEmployee(employee);
    }
}
