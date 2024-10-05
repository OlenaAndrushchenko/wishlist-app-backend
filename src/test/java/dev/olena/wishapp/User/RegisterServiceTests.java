package dev.olena.wishapp.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import dev.olena.wishapp.user.RegisterService;
import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserDTO;
import dev.olena.wishapp.user.UserRepository;

import java.util.Optional;

@SpringBootTest
public class RegisterServiceTests {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void testRegisterUserSuccessfully() {
        UserDTO userDTO = new UserDTO("newUser", "password123", "newuser@example.com");
        User savedUser = new User(1L, "newUser", passwordEncoder.encode("password123"), "newuser@example.com", null);
        
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        String result = registerService.save(userDTO);
        assertEquals("User newuser@example.com has been registered", result);
    }

    @Test
    void testRegisterUserWithExistingEmail() {
        UserDTO userDTO = new UserDTO("newUser", "password123", "existing@example.com");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            registerService.save(userDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("User with email existing@example.com already exists", exception.getReason());
    }

    @Test
    void testPasswordEncoding() {
        UserDTO userDTO = new UserDTO("newUser", "password123", "newuser@example.com");
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        
        registerService.save(userDTO);
        verify(passwordEncoder).encode("password123");
    }
}
