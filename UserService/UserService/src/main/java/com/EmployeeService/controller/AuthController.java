package com.EmployeeService.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EmployeeService.entity.Employee;
import com.EmployeeService.service.CustomUserDetailsService;
import com.EmployeeService.service.EmployeeService;
import com.EmployeeService.service.JwtService;
import com.EmployeeService.structure.ResponseStructure;
import com.UserService.dto.EmployeeDto;
import com.UserService.dto.LoginDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtService jwtservice;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<EmployeeDto>> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee savedEmployee = employeeService.RegisterEmployee(employeeDto);

        // Convert entity back to DTO
        EmployeeDto responseDto = new EmployeeDto();
        BeanUtils.copyProperties(savedEmployee, responseDto);

        ResponseStructure<EmployeeDto> response = new ResponseStructure<>();
        response.setMessage("Registered successfully");
        response.setSuccess(true);
        response.setData(responseDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> loginEmployee(@RequestBody LoginDto loginDto) {
        ResponseStructure<String> response = new ResponseStructure<>();

        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getEmail());

            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            
            // If successful, generate JWT token
            String token = jwtservice.generateToken(loginDto.getEmail(),role);

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

}
