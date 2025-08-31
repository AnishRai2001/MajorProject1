package com.EmployeeService.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EmployeeService.Repository.EmployeeRepository;
import com.EmployeeService.entity.Employee;
import com.UserService.dto.EmployeeDto;
@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Employee employee = employeeRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

	    if (!employee.isEnabled()) {
	        throw new RuntimeException("Email not verified");
	    }

	    return new User(
	        employee.getEmail(),
	        employee.getPassword(),
	        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + employee.getRole()))
	    );
	}
}
