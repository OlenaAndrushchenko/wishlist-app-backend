package dev.olena.wishapp.UserAccount;

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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {
    
    private MockMvc mockMvc;

    @Mock
    private RegisterService registerService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(new RegisterController(registerService))
            .build();
    }

    @Test
    public void testRegisterAccount() throws Exception {
        UserAccountDto userAccountDto = new UserAccountDto("user1Test", "passTest");
        when(registerService.save(any(UserAccountDto.class))).thenReturn("User userTest has been registered");

        ArgumentCaptor<UserAccountDto> captor = ArgumentCaptor.forClass(UserAccountDto.class);

        mockMvc.perform(post("/api/v1/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \"userTest\", \"password\": \"passTest\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().string("User userTest has been registered"));

        verify(registerService).save(captor.capture());
        assertEquals("userTest", captor.getValue().getUsername());
        assertEquals("passTest", captor.getValue().getPassword());
    }
}
