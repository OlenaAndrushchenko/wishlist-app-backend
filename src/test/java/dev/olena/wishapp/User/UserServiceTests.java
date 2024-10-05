package dev.olena.wishapp.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
