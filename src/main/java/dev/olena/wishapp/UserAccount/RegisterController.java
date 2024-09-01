package dev.olena.wishapp.UserAccount;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="api/v1/register")
public class RegisterController {
    
    RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserAccountDto userAccountDto) {
        String result = registerService.save(userAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result); /*registerService.save(userAccountDto);*/
    }
}
