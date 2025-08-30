package com.EmployeeService.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.EmployeeService.service.CustomUserDetailsService;
import com.EmployeeService.service.EmployeeService;
import com.EmployeeService.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter {

	  private JwtService jwtService;                    // ✅ no @Autowired
	    private CustomUserDetailsService userDetailsService; // ✅

	    
	    public JwtFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
	        this.jwtService = jwtService;
	        this.userDetailsService = userDetailsService;
	    }
	    
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String authHeader=request.getHeader("Authorization");
		String email=null;
		String token=null;
		
		if(authHeader!=null &&authHeader.startsWith("Bearer ")) {
			token=authHeader.substring(7);
			email = jwtService.validateTokenAndGetSubject(token);
			if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				 UserDetails userDetails=userDetailsService.loadUserByUsername(email);
				 
				 UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
		}
		
		 filterChain.doFilter(request, response);
	}

}
