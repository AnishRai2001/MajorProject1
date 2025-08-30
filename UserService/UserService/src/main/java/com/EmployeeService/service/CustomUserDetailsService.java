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
	
		Optional<Employee>user=employeeRepository.findByEmail(email);
		if(user.isPresent()) {
			   return new User(email, user.get().getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE" +user.get().getRole())));
		}
		   else {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	}

	}
}
