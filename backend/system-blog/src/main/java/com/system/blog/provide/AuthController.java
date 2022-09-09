package com.system.blog.provide;

import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.system.blog.business.model.Role;
import com.system.blog.business.model.User;
import com.system.blog.crosscutting.errormanagement.ErrorManager;
import com.system.blog.persistence.RoleRepository;
import com.system.blog.persistence.UserRepository;
import com.system.blog.provide.dto.LoginDto;
import com.system.blog.provide.dto.RegisterDto;
import com.system.blog.security.JwtTokenProvider;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Validated
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ErrorManager errorManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping(path = "/login", produces = { "application/json" })
	public ResponseEntity<String> authenticateUse(@RequestBody LoginDto loginDto) {

		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			
			System.out.println("TOKEN: "+token);
			
			return ResponseEntity.status(HttpStatus.OK).body(token);


		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}
	
	@PostMapping(path = "/register", produces = { "application/json" })
	public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
		
		
		try {
			if (userRepository.existsByUsername(registerDto.getUsername())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User exist");
			}
			
			if (userRepository.existsByEmail(registerDto.getEmail())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email user exist");
			}
			
			User user = User.builder()
					.name(registerDto.getName())
					.username(registerDto.getUsername())
					.email(registerDto.getEmail())
					.password(passwordEncoder.encode(registerDto.getPassword()))
					.build();
			
			Role role = roleRepository.findByName("ROLE_ADMIN").get();
			
			user.setRoles(Collections.singleton(role));
			
			
			userRepository.save(user);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("User register successfully");
					
		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

}
