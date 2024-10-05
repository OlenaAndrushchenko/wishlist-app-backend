package dev.olena.wishapp.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dev.olena.wishapp.user.AuthController;
import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserDTO;
import dev.olena.wishapp.utils.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {
    
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(authController)
            .build();
    }

    @Test
    @WithMockUser
    public void testLoginSuccess() throws Exception {
        UserDTO userDto = new UserDTO("testuser", "password", "user@example.com");
        UserDetails userDetails = new User(1L, "testuser", "password", "user@example.com", "1234abcd"); // Assuming User implements UserDetails
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null); // Change here
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(jwtUtil.generateToken(userDetails.getUsername())).thenReturn("dummy-token");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType("application/json")
                .content("{\"email\":\"user@example.com\",\"password\":\"password\"}")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer dummy-token"))
                .andExpect(content().string("Authentication successful for user: user@example.com"));
    }

}
