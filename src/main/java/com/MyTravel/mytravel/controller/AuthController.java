package com.MyTravel.mytravel.controller;

import com.MyTravel.mytravel.exception.ApiException;
import com.MyTravel.mytravel.exception.ErrorCode;
import com.MyTravel.mytravel.model.ERole;
import com.MyTravel.mytravel.model.Role;
import com.MyTravel.mytravel.model.User;
import com.MyTravel.mytravel.payload.request.LoginRequest;
import com.MyTravel.mytravel.payload.request.SignupRequest;
import com.MyTravel.mytravel.payload.response.MessageResponse;
import com.MyTravel.mytravel.payload.response.UserResponse;
import com.MyTravel.mytravel.repository.RoleRepository;
import com.MyTravel.mytravel.repository.UserRepository;
import com.MyTravel.mytravel.security.jwt.JwtUtils;
import com.MyTravel.mytravel.security.services.UserDetailsImpl;
import com.MyTravel.mytravel.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UserResponse(jwt,
												 userDetails.getId(),
												 userDetails.getFullName(),
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 userDetails.getPhoneNumber(),
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new ApiException(ErrorCode.USERNAME_ALREADY_EXIST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new ApiException(ErrorCode.EMAIL_ALREADY_EXIST);
		}

		if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
			throw new ApiException(ErrorCode.PHONE_ALREADY_EXIST);
		}

		if (CodeUtil.isNotValidPhone(signUpRequest.getPhoneNumber())) {
			throw new ApiException(ErrorCode.INVALID_PHONE);
		}

		// Create new user's account
		User user = new User(signUpRequest.getFullName(),
							signUpRequest.getUsername(),
							signUpRequest.getEmail(),
							encoder.encode(signUpRequest.getPassword()),
							signUpRequest.getPhoneNumber()
							);

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/logout")
	public ResponseEntity logoutUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(null);
		return ResponseEntity.ok(new MessageResponse("logout successful"));
	}
}
