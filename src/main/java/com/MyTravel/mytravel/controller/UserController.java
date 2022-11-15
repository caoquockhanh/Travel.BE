package com.MyTravel.mytravel.controller;

//import com.MyTravel.mytravel.model.MyUser;
import com.MyTravel.mytravel.model.User;
import com.MyTravel.mytravel.repository.UserRepository;
import com.MyTravel.mytravel.security.services.AuthService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    AuthService authService;

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiResponses(@ApiResponse(code = 404, message = "USER_NOT_FOUND"))
    public User getMyUser(Principal principal) {
        String getUsername = authService.getUsername(principal);
        return userRepository.findByUsername(getUsername).orElse(null);
    }

    @PutMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User updateMyUser(@RequestBody User user, Principal principal)
    {
        User oldUser = userRepository.findByUsername(authService.getUsername(principal)).orElse(null);
        oldUser.setFullName(user.getFullName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(oldUser);
    }

    @PutMapping("/password")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User updateMyPassword(@RequestBody String password, Principal principal)
    {
        User oldUser = userRepository.findByUsername(authService.getUsername(principal)).orElse(null);
        oldUser.setPassword(encoder.encode(password));
        return userRepository.save(oldUser);
    }
}
