package com.EmployeeService.service;


import com.EmployeeService.entity.Employee;
import com.UserService.dto.EmployeeDto;


public interface EmployeeService {
	Employee RegisterEmployee(EmployeeDto employeeDto);
	public boolean verifyEmail(String tokenStr) ;
	 public void createResetToken(String email) ;
	 public void resetPassword(String tokenStr, String newPassword);
}
