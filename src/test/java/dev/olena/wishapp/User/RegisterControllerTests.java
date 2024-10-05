package dev.olena.wishapp.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dev.olena.wishapp.user.RegisterController;
import dev.olena.wishapp.user.RegisterService;
import dev.olena.wishapp.user.UserDTO;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTests {
    
    private MockMvc mockMvc;

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(registerController)
            .build();
    }

    @Test
    @WithMockUser
    public void testRegisterUser() throws Exception {
        UserDTO userDto = new UserDTO("userTest", "passTest", "user@example.com");
        when(registerService.save(any(UserDTO.class))).thenReturn("User userTest has been registered");

        ArgumentCaptor<UserDTO> captor = ArgumentCaptor.forClass(UserDTO.class);

        mockMvc.perform(post("/api/v1/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"userTest\", \"password\": \"passTest\", \"email\": \"user@example.com\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().string("User userTest has been registered"));

        verify(registerService).save(captor.capture());
        assertEquals("userTest", captor.getValue().getUsername());
        assertEquals("passTest", captor.getValue().getPassword());
        assertEquals("user@example.com", captor.getValue().getEmail());
    }
}
