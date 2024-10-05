package dev.olena.wishapp.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dev.olena.wishapp.user.User;
import dev.olena.wishapp.user.UserController;
import dev.olena.wishapp.user.UserDTO;
import dev.olena.wishapp.user.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(new User(1L, "user", "pass", "user@example.com", ""), null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void testGetUserDetails() throws Exception {
        mockMvc.perform(get("/api/v1/account"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(content().json("{'username':'user','email':'user@example.com'}"));
    }

    @Test
    public void testUpdateUserDetails() throws Exception {
        UserDTO userDto = new UserDTO("updatedUser", "newPass", "updated@example.com");
        when(userService.updateUserDetails(eq(1L), any(UserDTO.class))).thenReturn("User details have been updated");

        mockMvc.perform(put("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"updatedUser\",\"password\":\"newPass\",\"email\":\"updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User details have been updated"));
    }
}
