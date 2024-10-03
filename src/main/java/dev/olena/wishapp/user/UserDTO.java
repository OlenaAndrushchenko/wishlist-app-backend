package dev.olena.wishapp.user;

import dev.olena.wishapp.utils.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDTO {
    
    @NotBlank(message = "Username cannot be empty", groups = ValidationGroups.Registration.class)
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters", groups = ValidationGroups.Registration.class)
    private String username;

    @NotBlank(message = "Password cannot be empty", groups = {ValidationGroups.Registration.class, ValidationGroups.Login.class})
    @Size(min = 5, message = "Password must be minimum of 5 characters", groups = {ValidationGroups.Registration.class, ValidationGroups.Login.class})
    private String password;

    @NotBlank(message = "Email cannot be empty", groups = {ValidationGroups.Registration.class, ValidationGroups.Login.class})
    private String email;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
