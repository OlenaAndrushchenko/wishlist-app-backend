package dev.olena.wishapp.UserAccount;

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
    public String register(@RequestBody UserAccountDto userAccountDto) {

        return registerService.save(userAccountDto);
    }
}
