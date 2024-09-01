package dev.olena.wishapp.UserAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserAccountDto {
    
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, message = "Password must be minimum of 5 characters")
    private String password;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
