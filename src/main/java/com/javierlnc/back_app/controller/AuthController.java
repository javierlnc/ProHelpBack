package com.javierlnc.back_app.controller;

import com.javierlnc.back_app.dto.AuthRequest;
import com.javierlnc.back_app.dto.AuthResponse;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.exception.BadPassword;
import com.javierlnc.back_app.repository.UserRepository;
import com.javierlnc.back_app.service.jwt.UserService;
import com.javierlnc.back_app.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest) {

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authRequest.getUsername());
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        String jwt = jwtUtil.generateToken(userDetails);
        return buildAuthResponse(user, jwt);
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch ( RuntimeException e) {
            throw new BadPassword("Contraseña incorrecta");
        }
    }

    private AuthResponse buildAuthResponse(User user, String jwt) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setRol(user.getRole());
        authResponse.setUserId(user.getId());
        authResponse.setUsername(user.getUsername());
        return authResponse;
    }
}
