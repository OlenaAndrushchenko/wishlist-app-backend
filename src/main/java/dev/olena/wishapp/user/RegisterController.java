package dev.olena.wishapp.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.olena.wishapp.utils.ValidationGroups;

@RestController
@RequestMapping(path ="/api/v1/register")
public class RegisterController {
    
    RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<String> register(@Validated(ValidationGroups.Registration.class) @RequestBody UserDTO userDto) {
        String result = registerService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
