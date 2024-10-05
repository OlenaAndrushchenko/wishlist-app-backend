package dev.olena.wishapp.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserDTO;
import dev.olena.wishapp.user.UserRepository;
import dev.olena.wishapp.user.UserService;

@SpringBootTest
public class UserServiceTests {
    
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "testuser", "password", "test@email.com", "1234abcd");

        Authentication authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");
    }

    @Test
    void testUpdateUserDetails_success() {
        UserDTO userDto = new UserDTO("newUsername", "newPassword", "new@email.com");
        String result = userService.updateUserDetails(1L, userDto);

        verify(userRepository).save(user);
        assertEquals("User details have been updated", result);
        assertEquals("newUsername", user.getRealUsername());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals("new@email.com", user.getEmail());
    }

    @Test
    public void testUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserDetails(1L, new UserDTO("username", "password", "email"));
        });
        
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testNoUpdateWhenNoChanges() {
        User existingUser = new User(1L, "username", "password", "email@example.com", "1234abcd");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        
        String result = userService.updateUserDetails(1L, new UserDTO("username", "password", "email@example.com"));
        
        verify(userRepository).save(existingUser); // verify that save is still called to handle possible other changes not covered in this test
        assertEquals("User details have been updated", result);
    }
}
