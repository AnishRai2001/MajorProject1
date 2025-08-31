package com.EmployeeService.controller;

import com.EmployeeService.entity.Employee;
import com.EmployeeService.service.EmployeeService;
import com.EmployeeService.service.JwtService;
import com.EmployeeService.service.CustomUserDetailsService;
import com.EmployeeService.structure.ResponseStructure;
import com.UserService.dto.EmployeeDto;
import com.UserService.dto.LoginDto;

import jakarta.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // ✅ Registration
    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<EmployeeDto>> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee savedEmployee = employeeService.RegisterEmployee(employeeDto);

        EmployeeDto responseDto = new EmployeeDto();
        BeanUtils.copyProperties(savedEmployee, responseDto);

        ResponseStructure<EmployeeDto> response = new ResponseStructure<>();
        response.setMessage("Registration successful. Please verify your email.");
        response.setSuccess(true);
        response.setData(responseDto);

        return ResponseEntity.ok(response);
    }

    // ✅ Email Verification
    @GetMapping("/verify")
    public ResponseEntity<ResponseStructure<String>> verifyEmail(@RequestParam String token) {
        employeeService.verifyEmail(token);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setMessage("Email verified successfully! You can now login");
        response.setSuccess(true);
        response.setData("Email verified");

        return ResponseEntity.ok(response);
    }

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(@RequestBody LoginDto loginDto) {
        ResponseStructure<String> response = new ResponseStructure<>();

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());
            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            String token = jwtService.generateToken(loginDto.getEmail(), role);

            response.setMessage("Login successful");
            response.setSuccess(true);
            response.setData(token);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMessage("Invalid username or password");
            response.setSuccess(false);
            response.setData(null);

            return ResponseEntity.status(401).body(response);
        }
    }

    // ✅ Forgot Password
    @Transactional
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseStructure<String>> forgotPassword(@RequestParam String email) {
        employeeService.createResetToken(email);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setMessage("Password reset link sent to email.");
        response.setSuccess(true);
        response.setData("Reset link sent");

        return ResponseEntity.ok(response);
    }

    // ✅ Reset Password
    @Transactional
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseStructure<String>> resetPassword(@RequestParam String token,
                                                                   @RequestParam String newPassword) {
        employeeService.resetPassword(token, newPassword);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setMessage("Password updated successfully.");
        response.setSuccess(true);
        response.setData("Password reset successful");

        return ResponseEntity.ok(response);
    }
}
