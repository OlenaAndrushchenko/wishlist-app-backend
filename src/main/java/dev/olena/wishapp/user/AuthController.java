package dev.olena.wishapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.olena.wishapp.utils.JwtUtil;
import dev.olena.wishapp.utils.ValidationGroups;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated(ValidationGroups.Login.class) @Valid @RequestBody UserDTO userDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok()
            .header("Authorization", "Bearer " + token)
            .body("Authentication successful for user: " + userDto.getEmail());
    }
}
