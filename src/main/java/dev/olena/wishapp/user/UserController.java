package dev.olena.wishapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserDetails() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UserDTO userDto = new UserDTO(currentUser.getRealUsername(), "", currentUser.getEmail());

        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<String> updateUserDetails(@Valid @RequestBody UserDTO userDto) {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Long userId = currentUser.getId();

        String result = userService.updateUserDetails(userId, userDto);
        return ResponseEntity.ok(result);
    }
}
