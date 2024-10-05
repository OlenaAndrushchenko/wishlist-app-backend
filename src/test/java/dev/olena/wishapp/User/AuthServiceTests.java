package dev.olena.wishapp.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.olena.wishapp.user.AuthService;
import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testLoadUserByUsernameSuccess() {
        User mockUser = new User(1L, "testuser", "password", "user@example.com", "1234");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));

        User result = (User) authService.loadUserByUsername("user@example.com");
        assertEquals("user@example.com", result.getUsername());
        assertEquals("testuser", result.getRealUsername());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername("nonexistent@example.com");
        });
    }
}
